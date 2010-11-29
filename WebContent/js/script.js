$(document).ready(
		function() {

			centerStatusBar();

			var page = 0;

			var topicOpening;

			var range;

			var topicList;

			var userList;

			var replyList;

			var xhEditor;

			init();

			function replyaTopic(topicId, content) {
				$.ajax({
					url : "ReplyaTopic",
					type : 'POST',
					global : false,
					dataType : "json",
					async : false,
					data : {
						topicId : topicId,
						content : content
					},
					beforeSend : function() {
						$(".statusBar").text("replying...");
						centerStatusBar();
						$(".statusBar").slideDown("slow");
					},
					success : function(data) {
						var flag = data.code;
						if (flag == 0) {
							$(".statusBar").text(data.message);
							centerStatusBar();
							$(".statusBar").slideDown("slow");
						}
						if (flag == 1) {
							$(".statusBar").text(data.message);
							centerStatusBar();
							$(".statusBar").slideUp("slow");
						}
					}
				});
			}

			function showTopic(title, author, date, content) {
				var topicString = "<div class=\"right_topic\"><h3>" + title + "</h3><ul><li>post by : " + author + "</li><li>" + date + "</li></ul><div class=\"right_content\">" + content
						+ "</div></div>";
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
					addRelpy(replyList[i].content, userList[i + 1].name, replyList[i].date)
				}
				showForm();
			}

			function getReply(topicId) {
				$.ajax({
					url : "getReply",
					type : 'POST',
					global : false,
					dataType : "json",
					async : false,
					data : {
						topicId : topicId
					},
					beforeSend : function() {
						$(".statusBar").text("getting replies...");
						centerStatusBar();
						$(".statusBar").slideDown("slow");
					},
					success : function(data) {
						var flag = data.code;
						if (flag == 0) {
							$(".statusBar").text(data.message);
							centerStatusBar();
							$(".statusBar").slideDown("slow");
						}
						if (flag == 1) {
							$(".statusBar").slideUp("slow");
							userList = data.message;
							replyList = data.message2;
						}
					}
				});
			}

			function boundItemClick() {
				$(".item").click(function() {
					var id = $(this).attr("id");
					var index = $(this).attr("name");
					topicOpening = id;
					getReply(id);
					showReply(id, index);
					boundSubmitClick();
					xhEditor = $('#right_textarea').xheditor({
						skin : 'nostyle',
						tools : 'mini'
					});
				});
			}

			function boundSubmitClick() {
				$("#submit").click(function() {
					var topicId = topicOpening;
					var content = xhEditor.getSource();
					replyaTopic(topicId, content);
				});
			}

			function init() {
				$(".item").remove();
				range = "all";
				getJSON(range);
				addAllItems(topicList);
				boundItemClick();
			}

			function addItem(topicId, title, replyCount, date, index) {
				var item = "<div id=\"" + topicId + "\" name=\"" + index + "\" class=\"item\"><div class=\"replyCount\"><div class=\"count\">" + replyCount
						+ "</div><div class=\"text_replies\">replies</div></div>" + "<div class=\"titleWrapper\"><div class=\"item_title\">" + title + "</div><div class=\"item_time\">" + date
						+ "</div></div></div>";
				$(".topicList").append(item);
			}

			function addAllItems(topicList) {
				for ( var i = 0; i < topicList.length; i++) {
					addItem(topicList[i].id, topicList[i].title, topicList[i].replyCount, topicList[i].date, i);
				}
			}

			function getJSON(range) {
				$.ajax({
					url : "getTopic",
					type : 'POST',
					global : false,
					dataType : "json",
					async : false,
					data : {
						range : range,
						page : page
					},
					beforeSend : function() {
						$(".statusBar").text("getting topics...");
						centerStatusBar();
						$(".statusBar").slideDown("slow");
					},
					success : function(data) {
						var flag = data.code;
						if (flag == 0) {
							$(".statusBar").text(data.message);
							centerStatusBar();
							$(".statusBar").slideDown("slow");
						}
						if (flag == 1) {
							$(".statusBar").slideUp("slow");
							topicList = data.message;
						}
					}
				});
			}

			function resizeContainer() {
				var windowHeight = $(window).height();
				$("div.container").height(windowHeight - 119);
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

			$("#viewMine").click(function() {
				page = 0;
				$(".item").remove();
				range = "user";
				getJSON(range);
				addAllItems(topicList);
				boundItemClick();
			});

			$(".next").click(function() {
				if (topicList.length < 10) {
					$(".statusBar").text("no more topics!");
					centerStatusBar();
					$(".statusBar").slideDown("slow");
				} else {
					page = page + 1;
					$(".item").remove();
					getJSON(range);
					addAllItems(topicList);
					boundItemClick();
				}
			});

			$(".prev").click(function() {
				if (page == 0) {
					$(".statusBar").text("no previous topics!");
					centerStatusBar();
					$(".statusBar").slideDown("slow");
				} else {
					page = page - 1;
					$(".item").remove();
					getJSON(range);
					addAllItems(topicList);
					boundItemClick();
				}
			});

		});