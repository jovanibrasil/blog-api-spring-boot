$(function(){
	// Hide all notification messages on mouse click
	$("messages li").click(function(){
		$(this).fadeOut();
	});
	// Automatically hide all notification messages
	setTimeout(function(){
		$('#messages li.info').fadeOut();
	}, 3000);
});