var TAG = "show notification";

function showAlert(message, title, buttonLabel) {
	navigator.notification.alert(
			message,	//message
			alertDismiss,	//callback to invoke with button pressed
			title,	//title
			buttonLabel //buttonLabel
			);
}

function alertDismiss() {
	console.log("alert dismissing!");
}

function showConfirm(message, title, buttonLabels) {
	navigator.notification.confirm(
			 	message,  // message
		        onConfirm,              // callback to invoke with index of button pressed
		        title,            // title
		        buttonLabels          // buttonLabels
		    );	
}

function onConfirm(button) {
	console.log("clicked button: " + button);
}

function beep(count) {
	navigator.notification.beep(count);
}

function vibrate(millisecond) {
	navigator.notification.vibrate(millisecond);
}