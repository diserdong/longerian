var TAG = "store to media player";

var myMedia = null;
var timer = null;
var dur = -1;
var isPaused = false;
var recentlyPlayed = null;

function play(src) {
	if(myMedia === null) {
		if(src == undefined)
			return 0;
		console.log("src is: " + src);
		myMedia = new Media(src, onSuccess, onError);
		myMedia.play();
	} else {
		if(isPaused) {
			isPaused = false;
			myMedia.play();
		}
	}
	
	if(timer === null) {
		timer = setInterval(function() {
			myMedia.getCurrentPosition(
					// success callback
					function(position) {
                        if (position > -1) {
                            setAudioPosition(position);
                            if (dur <= 0) {
                                dur = myMedia.getDuration();                             
                                if (dur > 0) {
                                	setDuration(dur);
                                }
                            }   
                        }
                    },
                    // error callback
                    function(e) {
                        console.log("Error getting pos=" + e);
                    }
			);
		}, 
		1000);
	}
	
}

function pause() {
	if(myMedia) {
		myMedia.pause();
		isPaused = true;
	}
}

function stop() {
	if(myMedia) {
		myMedia.stop();
		myMedia.release();
	}
	if(timer) {
		clearInterval(timer);
		timer = null;
	}
	isPaused = false;
}

function setDuration(duration) {
	document.getElementById('duration').innerHTML = (duration / 1000) + " sec";
}

function setAudioPosition(position) {
	document.getElementById('current_position').innerHTML = position / 1000 + " sec";
}

function onSuccess() {
	 console.log("playAudio():Audio Success");
	 setAudioPosition(dur);
	 clearInterval(timer);
	 timer = null;
	 myMedia = null;
	 isPaused = false;
	 dur = -1;
}

function onError(error) {
	//console.log('code: ' + error.code + '\n' + 'message: ' + error.message + '\n');
	clearInterval(timer);
	timer = null;
	myMedia = null;
	isPaused = false;
	setAudioPosition(0);
}