var result = null; // 存储用户测验数据

$(document).ready(function(){

	$(".btn-next").click(function() {
		var text = $(this)
			.parents(".block")
			.find(".user-num-text")
			.val().trim();

		if (text == "") {
			alert("请填写学号")
			return;
		}

		result = {
			user: {
				text: text
			},
			data: []
		}

		showInstruction()
	})

	$(window).keypress(function(e) {
		 if (e.which == 32) {
		 	var currBlock = $(".block:visible")
		 	
		 	if (currBlock.hasClass("instruction")) {
		 		paintSlide(0)
		 		showMain()
		 	} else if (currBlock.hasClass("main")) {
		 		var type = currBlock.data("type")
		 		if (type == 1) {
		 			currBlock.find(".video")[0].play()
		 		}
		 	}
		 }
	})
});

function showInstruction() {
	$(".login").slideUp(300, function() {
		$(".instruction").slideDown(300)
	})
}

function showMain() {
	$(".instruction").slideUp(300, function() {
		$(".main").slideDown(300, function() {
			resizeVideo()
		})
	})
}

function nextSlide() {
	var main = $(".main")
	var type = main.data("type")

	main.slideUp(300, function() {
		if (type == 2) {
			var radio = main.find(".option-input:checked")

			result.data.push({
				name: radio.data("item-name"),
				val: radio.val()
			})

		}

		var index = main.data("index")
		index++

		if (index >= contentArr.length) {
			sendResult()
			$(".result").slideDown(300)

		} else {
			paintSlide(index)
			main.slideDown(300, function () {
				resizeVideo()
			})
		}
	})
}

function paintSlide(currIndex) {

	var main = $(".main")
	var title = main.find(".title")
	var content = main.find(".content")
		.empty()

	var data = contentArr[currIndex]

	main
		.data("index", currIndex)
		.data("type", data.type)

	if (data.type == 1) {
		title.text("按空格键播放视频")

		$("<video></video>")
			.attr("src", "assets/videos/" + data.video.src)
			.attr("controls", "controls")
			.addClass("video")
			.appendTo(content)
			.bind("ended", function(){
				nextSlide()
			})


	} else if (data.type == 2) {
		title.text("看完这段视频后，你的感觉是？")

		$.each(data.options, function(i, item) {
			var row = $("<div class='option-row'></div>")
				.appendTo(content)

			var radio = $("<input type='radio' />")
				.addClass("option-input")
				.attr("name", "option-radio")
				.data("item-name", data.name)
				.val(item.id)
				.appendTo(row)
				.click(function(e) {
					nextSlide()

					e.stopPropagation()
				})

			$("<label></label>")
				.addClass("option-label")
				.text(item.text)
				.appendTo(row)
				.click(function(e) {
					radio.click()

					e.stopPropagation()
				})

			row.click(function(e) {
				radio.click()

				e.stopPropagation()
			})
		})
	}

}

function resizeVideo() {
	var content = $(".main .content")

	var width = content.width()
	var height = content.height()

	content.find(".video")
		.attr("width", width)
		.attr("height", height)

}

function sendResult() {

	var websocket;

	var progress = new Progress($(".panel"));
	if ('WebSocket' in window) {
		websocket = new WebSocket(config.url);

		progress.showProgress("正在保存数据， 请稍等！")
		websocket.onopen = function(){
			var data = JSON.stringify(result)
			websocket.send(data)
			console.log(config.url)
			console.log(data)			
		}

		websocket.onmessage = function(e) {
			progress.hideProgress()
			var obj = JSON.parse(e.data)
			alert(obj.data)
			websocket.close()
		}

		websocket.onerror = function(e) {
			progress.hideProgress()
			alert("发送数据失败！" + e)
			websocket.close()
		}


	} else {
		alert("Not support websocket")
	}

}