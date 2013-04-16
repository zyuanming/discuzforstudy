function get(id) {
	return !id ? null : document.getElementById(id);
}
function $(id) {
	return !id ? null : document.getElementById(id);
}

function display(id) {
	var obj = document.getElementById(id);
	if(obj.style.visibility) {
		obj.style.visibility = obj.style.visibility == 'visible' ? 'hidden' : 'visible';
	} else {
		obj.style.display = obj.style.display == '' ? 'none' : '';
	}
}
function htmlspecialchars(str) {
	return preg_replace(['&', '<', '>', '"'], ['&amp;', '&lt;', '&gt;', '&quot;'], str);
}