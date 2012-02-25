$(document).ready(function() {
	//导航栏事件绑定
	$('#nav-main').focus();
	$('#update-lib').hide();
	
	$('#nav-main').live('tap click', function() {
		$('#update-lib').hide();
		$('#main-panel').show();
	});
	
	$('#nav-update').live('tap click', function() {
		$('#main-panel').hide();
		$('#update-lib').show();
	});
	
	//listview事件绑定
//	$('#list-view').live('swipeleft', function() {
//		queryFolder(['folder']);
//	});
	
	//三个音乐控制按钮绑定事件
	$('#play').live('tap click', function() {
		play();
	});
	
	$('#pause').live('tap click', function() {
		pause();
	});
	
	$('#stop').live('tap click', function() {
		stop();
	});
}
);

function displayFolders(results) {
	var len = results.rows.length;
	var i;
    var element = document.getElementById("list");
    if(element.hasChildNodes()) {
        while(element.childNodes.length >= 1) {
        	element.removeChild(element.firstChild);       
        } 
    }
	for (var i=0; i < len; i++) {
		var item = results.rows.item(i).folder;
	    var liElement = document.createElement("li");
		liElement.id = "fold_id_" + i;
		liElement.innerHTML = item;
        (function() {
        	var folder = results.rows.item(i).folder;
        	var where = new Array();
        	where["folder"] = folder;
			liElement.onclick = function () {querySong(['name', 'path'], where)};
        })(i);
	    element.appendChild(liElement);
	}
	$('#list').listview('refresh');
}

function displaySongs(results) {
	var len = results.rows.length;
	var i;
    var element = document.getElementById("list");
    if(element.hasChildNodes()) {
        while(element.childNodes.length >= 1) {
        	element.removeChild(element.firstChild);       
        } 
    }
    var liElement = document.createElement("li");
	liElement.id = "back";
	liElement.innerHTML = "back to folder view";
	liElement.onclick = function () {queryFolder(['folder']);};
	element.appendChild(liElement);
	for (var i=0; i < len; i++) {
		var item = results.rows.item(i).name;
	    var liElement = document.createElement("li");
		liElement.id = "song_id_" + i;
		liElement.innerHTML = item;
        (function() {
        	var song = results.rows.item(i).path;
			liElement.onclick = function () {play(song)};
        })(i);
	    element.appendChild(liElement);
	}
	$('#list').listview('refresh');
}
