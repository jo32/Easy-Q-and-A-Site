String.format = function(src){
    if (arguments.length == 0) return null;
    var args = Array.prototype.slice.call(arguments, 1);
    return src.replace(/\{(\d+)\}/g, function(m, i){
        return args[i];
    });
};

$(document).ready(function() {

	centerStatusBar();

	var xhEditor;

	function validateInput() {
		var flag = 1;
		message = "content fetched.";
		var title = $("#titleInput").val();
		var content = xhEditor.getSource();
		return [ flag, message, title, content ];
	}

	function resizeContainer() {
		var windowHeight = $(window).height();
		$("div.container").height(windowHeight - 147);
	}

	function centerStatusBar() {
		var ml = ($(window).width() - $(".statusBar").width()) / 2;
		$(".statusBar").css("margin-left", ml + "px");
	}

	function resizeForm() {
		var parentHeight = $(".container").height();
		var titleHeight = $("#newTopic").parent().find(".newTopicTitle").outerHeight();
		$("#newTopic").height(parentHeight - titleHeight - 10);
		var twidth = $("#titleInput").outerWidth();
		$('#contentText').width(twidth);
		var ph = $("#newTopic").height();
		var h1 = $(".titleDiv").outerHeight();
		var h2 = $("#h3t").outerHeight();
		var h3 = $("#submit").outerHeight();
		$('#contentText').height(ph - h1 - h2 - h3 - 60);
		xhEditor = $('#contentText').xheditor({
			skin : 'nostyle',
			tools : 'simple'
		});
	}

	$(window).load(function() {
		resizeContainer();
		resizeForm();
		var optionsMargin = ($(".options").parent().height() - $(".options").height()) / 2;
		$("div.options").css("margin-top", optionsMargin + "px");
	});

	$(window).resize(function() {
		centerStatusBar();
		resizeContainer();
		resizeForm();
	});

	$("#submit").click(function() {
		var result = validateInput();
		if (result[0] == 0) {
			$(".statusBar").text(result[1]);
			centerStatusBar();
			$(".statusBar").slideDown("slow");
		}
		if (result[0] == 1) {
			$(".statusBar").slideUp("slow");
			$.ajax({
				url : "newTopic",
				type : 'POST',
				global : false,
				dataType : "json",
				data : {
					title : result[2],
					content : result[3]
				},
				beforeSend : function() {
					$(".statusBar").text("Creating new topic...");
					centerStatusBar();
					$(".statusBar").slideDown("slow");
				},
				success : function(data) {
					var flag = data.code;
					if (flag == 0) {
						$(".statusBar").text(data.message);
						centerStatusBar();
						$(".statusBar").slideDown("slow");
					} else if (flag == 1) {
						$(".statusBar").text(data.message);
						centerStatusBar();
						$(".statusBar").slideDown("slow");
						window.location.replace("home.jsp");
					} else {
						$(".statusBar").text(data.message);
						centerStatusBar();
						$(".statusBar").slideDown("slow");
					}
				}
			});
		}
	});
});