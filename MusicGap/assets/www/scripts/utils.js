function extractPath(fullPath, prePath) {
	var result = fullPath.substr(prePath.length);
	return result;
}

function extractName(fullPath) {
	var result = fullPath.substr(fullPath.lastIndexOf("/") + 1); 
	return result;
}

function extractFolder(fullPath, prePath) {
	var path = extractPath(fullPath, prePath);
	var name = extractName(fullPath);
	var folder = path.substring(0, path.indexOf(name));
	if(folder == "") //sdcard root
		folder = "/";
	return folder;
}

//TODO unfinished
function formatTime(milliseconds) {
	var hour = "";
	var minute = "";
	var second = "";
	hour = floor(milliseconds / 3600);
	minute = milliseconds % 60 / 60;
	second = milliseconds % 0 % 60;
	alert(hour + " " + minute + " " + second);
}