String.format = function(src) {
	if (arguments.length == 0)
		return null;
	var args = Array.prototype.slice.call(arguments, 1);
	return src.replace(/\{(\d+)\}/g, function(m, i) {
		return args[i];
	});
};

var page = 0;

var topicOpening;

var range;

var topicList;

var userList;

var replyList;

var xhEditor;

var id;

var index;

var sortby = "time";

$(document).ready(function() {

	centerStatusBar();

	init();

	function replyTopic(topicId, content) {
		$.ajax({
			url : "replyTopic",
			type : 'POST',
			global : true,
			dataType : "json",
			async : true,
			data : {
				topicId : topicId,
				content : content
			},
			beforeSend : function() {
				$(".statusBar").text("replying...");
				centerStatusBar();
				$(".statusBar").css("display", "block");
			},
			success : function(data) {
				var flag = data.code;
				if (flag == 0) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "block");
				}
				if (flag == 1) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "none");
					var count = $("#" + topicId + " .replyCount .count").text();
					$("#" + topicId + " .replyCount .count").text(parseInt(count) + 1);
					getReply(topicId);
				}
			}
		});
	}

	function showTopic(title, author, date, content) {
		var topicString = "<div class=\"right_topic\"><h3>" + title + "</h3><ul><li>post by : " + author + "</li><li>" + date + "</li></ul><div class=\"right_content\">" + content + "</div></div>";
		$(".right").append(topicString);
	}

	function addRelpy(content, author, date) {
		var replyString = "<div class=\"reply\"><div class=\"replyContent\">" + content + "</div><ul><li>post by : " + author + "</li><li>" + date + "</li></ul></div>";
		$(".right").append(replyString);
	}

	function showForm() {
		var formString = "<form id=\"replyatopic\"><textarea id=\"right_textarea\"></textarea><div id=\"submit\">reply</div></form>";
		$(".right").append(formString);
	}

	function showReply(topicId, index) {
		$(".right_topic").remove();
		$(".reply").remove();
		$("#replyatopic").remove();
		var title = topicList[index].title;
		var content = topicList[index].content;
		var date = topicList[index].date;
		var author = userList[0].name;
		showTopic(title, author, date, content);
		for ( var i = 0; i < replyList.length; i++) {
			addRelpy(replyList[i].content, userList[i + 1].name, replyList[i].date);
		}
		showForm();
	}

	function getReply(topicId, userId) {
		$.ajax({
			url : "getReply",
			type : 'POST',
			global : true,
			dataType : "json",
			async : true,
			data : {
				topicId : topicId,
				userId : userId
			},
			beforeSend : function() {
				$(".statusBar").text("getting replies...");
				centerStatusBar();
				$(".statusBar").css("display", "block");
			},
			success : function(data) {
				var flag = data.code;
				if (flag == 0) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "block");
				}
				if (flag == 1) {
					$(".statusBar").css("display", "none");
					userList = data.message;
					replyList = data.message2;
					showReply(id, index);
					boundSubmitClick();
					xhEditor = $('#right_textarea').xheditor({
						skin : 'nostyle',
						tools : 'mini'
					});
				}
			}
		});
	}

	function boundItemClick() {
		$(".item").click(function() {
			id = $(this).attr("id");
			index = $(this).attr("name");
			var userId = topicList[index].userId;
			topicOpening = id;
			getReply(id, userId);
		});
	}

	function boundSubmitClick() {
		$("#submit").click(function() {
			var topicId = topicOpening;
			var content = xhEditor.getSource();
			replyTopic(topicId, content);
		});
	}

	function init() {
		$(".item").remove();
		range = "all";
		getJSON(range);
	}

	function addItem(topicId, title, replyCount, diggCount, date, index) {
		var item = "<div id=\"{0}\" name=\"{1}\" class=\"item\"><div class=\"diggCount\"><div class=\"count\">{5}</div><div class=\"text_diggs\">diggs</div></div><div class=\"replyCount\"><div class=\"count\">{2}</div><div class=\"text_replies\">replies</div></div><div class=\"titleWrapper\"><div class=\"item_title\">{3}</div><div class=\"item_time\">{4}</div></div></div>";
		item = String.format(item, topicId, index, replyCount, title, date, diggCount);
		$(".topicList").append(item);
	}

	function addAllItems(topicList) {
		for ( var i = 0; i < topicList.length; i++) {
			addItem(topicList[i].id, topicList[i].title, topicList[i].replyCount, topicList[i].diggCount, topicList[i].date, i);
		}
	}

	function getJSON(range) {
		$.ajax({
			url : "getTopic",
			type : 'POST',
			global : true,
			dataType : "json",
			async : true,
			data : {
				range : range,
				page : page,
				sortby : sortby
			},
			beforeSend : function() {
				$(".statusBar").text("getting topics...");
				centerStatusBar();
				$(".statusBar").css("display", "block");
			},
			success : function(data) {
				var flag = data.code;
				if (flag == 0) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "block");
				}
				if (flag == 1) {
					$(".statusBar").css("display", "none");
					topicList = data.message;
					addAllItems(topicList);
					boundItemClick();
				}
			}
		});
	}

	function resizeContainer() {
		var windowHeight = $(window).height();
		$("div.container").height(windowHeight - 147);
	}

	function resizeContentWrapper() {
		var parentHeight = $(".container").height();
		var titleHeight = $(".contentWrapper").parent().find(".topicTitle").outerHeight();
		$(".contentWrapper").height(parentHeight - titleHeight - 10);
	}

	function centerStatusBar() {
		var ml = ($(window).width() - $(".statusBar").width()) / 2;
		$(".statusBar").css("margin-left", ml + "px");
	}

	$(window).load(function() {
		resizeContainer();
		var optionsMargin = ($(".options").parent().height() - $(".options").height()) / 2;
		$("div.options").css("margin-top", optionsMargin + "px");
		resizeContentWrapper();
	});

	$(window).resize(function() {
		centerStatusBar();
		resizeContainer();
		resizeContentWrapper();
	});

	$("#byTime").click(function() {
		page = 0;
		$(".item").remove();
		sortby = "time";
		getJSON(range);
	});

	$("#byDiggs").click(function() {
		page = 0;
		$(".item").remove();
		sortby = "diggs";
		getJSON(range);
	});

	$("#byReplies").click(function() {
		page = 0;
		$(".item").remove();
		sortby = "replies";
		getJSON(range);
	});

	$("#viewMine").click(function() {
		page = 0;
		$(".item").remove();
		range = "user";
		getJSON(range);
	});

	$("#viewAll").click(function() {
		page = 0;
		$(".item").remove();
		range = "all";
		getJSON(range);
	});

	$("#digg").click(function() {
		$.ajax({
			url : "digg",
			type : 'POST',
			global : true,
			dataType : "json",
			async : true,
			data : {
				topicId : topicOpening
			},
			beforeSend : function() {
				$(".statusBar").text("digging...");
				centerStatusBar();
				$(".statusBar").css("display", "block");
			},
			success : function(data) {
				var flag = data.code;
				if (flag == 0) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "block");
				}
				if (flag == 1) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "none");
					var count = $("#" + topicOpening + " .diggCount .count").text();
					$("#" + topicOpening + " .diggCount .count").text(parseInt(count) + 1);
				}
			}
		});
	});

	$("#delete").click(function() {
		$.ajax({
			url : "delete",
			type : 'POST',
			global : true,
			dataType : "json",
			async : true,
			data : {
				topicId : topicOpening
			},
			beforeSend : function() {
				$(".statusBar").text("deleting...");
				centerStatusBar();
				$(".statusBar").css("display", "block");
			},
			success : function(data) {
				var flag = data.code;
				if (flag == 0) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "block");
				}
				if (flag == 1) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "none");
					$("#" + topicOpening).remove();
					$(".right_topic").remove();
					$(".reply").remove();
					$("#replyatopic").remove();
				}
			}
		});
	});

	$("#edit").click(function() {
		$.ajax({
			url : "editPermission",
			type : 'POST',
			global : true,
			dataType : "json",
			async : true,
			data : {
				topicId : topicOpening
			},
			beforeSend : function() {
				$(".statusBar").text("requesting...");
				centerStatusBar();
				$(".statusBar").css("display", "block");
			},
			success : function(data) {
				var flag = data.code;
				if (flag == 0) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "block");
				}
				if (flag == 1) {
					$(".statusBar").text(data.message);
					centerStatusBar();
					$(".statusBar").css("display", "none");
					window.location.replace("edit.jsp");
				}
			}
		});
	});

	$(".next").click(function() {
		if (topicList.length < 10) {
			$(".statusBar").text("no more topics!");
			centerStatusBar();
			$(".statusBar").css("display", "block");
		} else {
			page = page + 1;
			$(".item").remove();
			getJSON(range);
		}
	});

	$(".prev").click(function() {
		if (page == 0) {
			$(".statusBar").text("no previous topics!");
			centerStatusBar();
			$(".statusBar").css("display", "block");
		} else {
			page = page - 1;
			$(".item").remove();
			getJSON(range);
		}
	});

});