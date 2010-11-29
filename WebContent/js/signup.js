$(document).ready(function() {

	centerStatusBar();

	function validateInput() {
		var flag = 1;
		var message = "";
		emailReg = /^\w{3,}@\w+(\.\w+)+$/;
		var email = $("#email").val();
		if (!emailReg.test(email)) {
			message = "Wrong Email!";
			flag = 0;
		}
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
		rePasswordReg = /^[a-z,A-Z,0-9]+$/;
		var rePassword = $("#rePassword").val();
		if (!nameReg.test(rePassword)) {
			message = "Wrong rePassword!";
			flag = 0;
		}
		if (password != rePassword) {
			message = "The two passwords are not matched!";
			flag = 0;
		}
		return [ flag, message, name, email, password ];
	}

	function resizeContainer() {
		var windowHeight = $(window).height();
		$("div.container").height(windowHeight - 119);
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
				url : "signup",
				type : 'POST',
				global : false,
				dataType : "json",
				data : {
					name : result[2],
					email : result[3],
					password : result[4]
				},
				beforeSend : function() {
					$(".statusBar").text("Signing up!");
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
						window.location.replace("signin.jsp");
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