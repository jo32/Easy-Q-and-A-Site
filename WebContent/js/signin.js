String.format = function(src){
    if (arguments.length == 0) return null;
    var args = Array.prototype.slice.call(arguments, 1);
    return src.replace(/\{(\d+)\}/g, function(m, i){
        return args[i];
    });
};

$(document).ready(function() {

	centerStatusBar();

	function validateInput() {
		var flag = 1;
		var message = "";
		nameReg = /^[a-z,A-Z,0-9]+$/;
		var name = $("#name").val();
		if (!nameReg.test(name)) {
			message = "Wrong User Name!";
			flag = 0;
		}
		passwordReg = /^[a-z,A-Z,0-9]+$/;
		var password = $("#password").val();
		if (!nameReg.test(password)) {
			message = "Wrong Password!";
			flag = 0;
		}
		return [ flag, message, name, password ];
	}

	function resizeContainer() {
		var windowHeight = $(window).height();
		$("div.container").height(windowHeight - 147);
	}

	function centerStatusBar() {
		var ml = ($(window).width() - $(".statusBar").width()) / 2;
		$(".statusBar").css("margin-left", ml + "px");
	}

	$(window).load(function() {
		resizeContainer();
		var optionsMargin = ($(".options").parent().height() - $(".options").height()) / 2;
		$("div.options").css("margin-top", optionsMargin + "px");
	});

	$(window).resize(function() {
		centerStatusBar();
		resizeContainer();
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
				url : "signin",
				type : 'POST',
				global : false,
				dataType : "json",
				data : {
					name : result[2],
					password : result[3]
				},
				beforeSend : function() {
					$(".statusBar").text("Signing in!");
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