var Utils = {
    renderFieldErrorTooltip: function (selector, msg, placement) {
        var elem;
        if (typeof placement === 'undefined') {
            placement = 'right'; // default to right-aligned tooltip
        }
        elem = $(selector);
        elem.tooltip({'title': msg, 'trigger': 'manual', 'placement': placement});
        elem.tooltip('show');
        elem.addClass('error');
        elem.on('focus click', function(e) {
            elem.removeClass('error');
            elem.tooltip('hide');
        });
    }
};
$(document).ready(function(){

    $.ajax({
                url: 'http://testtiweb.appspot.com/api/subject',
                dataType: 'json',
                contentType: "application/json",
                data: {"q": JSON.stringify({"filters": filters, "disjunction":true, "page":page})},
                complete: function(data){
                    var a=$.parseJSON(data.responseText)
                    var box = $("#rs_container")
                    $.each(a.objects, function(idx,obj){
                        box.append("<div class='blog-post'>" +
                            "<h4 class='blog-post-title'>"+(idx+1)+"."+obj.contents+"</h4>" +
                            "<p class='blog-post-meta'>The answer is "+obj.answer+".</p>" +
                            "<ol class='list-unstyled'>" +
                            "<li>A."+obj.option1+"</li>" +
                            "<li>B."+obj.option2+"</li>" +
                            "<li>C."+obj.option3+"</li>" +
                            "<li>D."+obj.option4+"</li>" +
                            "</ol>" +
                            "</div>");
                    });

                }
            });

    $.ajax({
                url: 'http://testtiweb.appspot.com/api/subject',
                dataType: 'json',
                contentType: "application/json",
                data: {"q": JSON.stringify({"filters": filters, "disjunction":true})},
                complete: function(data){
                    var a=$.parseJSON(data.responseText)
                    var box = $("#testPaperPart").find("form")
                    $.each(a.objects, function(idx,obj){
                        box.append("<div class='questionArea question'>" +
                                "<div class='questionText'>" +
                                "<div class='serialMark serialNumber'>"+(idx+1)+".</div>" +
                                "<div class='questionContent'>"+obj.contents+"</div>" +
                                "</div>" +
                                "<div class='partition'></div>" +
                                "<div class='optionArea'>" +
                                "<div  name='"+obj.id+"' class='A option'>" +
                                "<input type='radio' name='"+obj.id+"' class='radio'/>" +
                                "<div class='serialMark serialOption'>A.</div>" +
                        "<div class='questionContent optionContent'>"+obj.option1+"</div>" +
                                "</div>" +
                        "<div  name='"+obj.id+"' class='B option'>" +
                                "<input type='radio' name='"+obj.id+"' class='radio'/>" +
                                "<div class='serialMark serialOption'>B.</div>" +
                        "<div class='questionContent optionContent'>"+obj.option2+"</div>" +
                                "</div>" +
                                "<div  name='"+obj.id+"' class='C option'>" +
                                "<input type='radio' name='"+obj.id+"' class='radio'/>" +
                                "<div class='serialMark serialOption'>C.</div>" +
                        "<div class='questionContent optionContent'>"+obj.option3+"</div>" +
                                "</div>" +
                                "<div  name='"+obj.id+"' class='D option'>" +
                                "<input type='radio' name='"+obj.id+"' class='radio'/>" +
                                "<div class='serialMark serialOption'>D.</div>" +
                        "<div class='questionContent optionContent'>"+obj.option4+"</div>" +
                                "</div></div>"+
                            "<div class='partition'></div>" +
                                "<div class='chosenAnswer'>"+
                                "<div class='Answer firstAnswer A'>A</div>" +
                                "<div class='Answer B'>B</div>" +
                                "<div class='Answer C'>C</div>" +
                                "<div class='Answer D'>D</div>" +
                                "</div>" +
                            "<div class='right_answer'>ANSWER:<div>"+obj.answer+"</div></div>"+
                            "</div>");
                    });
                }
            });
    setTimeout(function(){    MathJax.Hub.Queue(["Typeset",MathJax.Hub]); },2500);
	var aSpan = $(".clockNum");

	var c=0;
    var isTimer=true;
	t=setInterval(getTimes, 1000);
    if (aSpan == undefined){
        clearInterval(t);
    }
	getTimes();
	function getTimes ()
	{
        c++;
        var timer = new Array(2);
        timer[0]=parseInt(c%3600/60);
        timer[1]=c%60;
		for (var i in timer) aSpan[i].innerHTML = timer[i].toString().replace(/^(\d)$/, "0$1");
	}


    $("#pauseButton").mouseover(function(){
        this.style.cursor = "hand";
        $("#pauseButton").css({color:"white",background:"#1874CD"});

    });
    $("#pauseButton").mouseout(function(){
        if(isTimer === true){
            $("#pauseButton").css({color:"#1874CD",background:"lightgrey"});
        } else {
            $("#pauseButton").css({color:"white",background:"#1874CD"});
        }
    });

    $("#pauseButton").click(function(){
        if(isTimer === true){
            clearInterval(t);
            isTimer = false;
            $("#pauseButton").text("Continue");
            $("#pauseButton").css({color:"white",background:"#1874CD"});
        } else {
            t=setInterval(getTimes, 1000);
            isTimer = true;
            $("#pauseButton").text("Pause");
            $("#pauseButton").css({color:"#1874CD",background:"lightgrey"});
        }
    });
    var hand_in = $("#handInTestPaperButton");
    hand_in.mouseover(function(){
        hand_in.css({color:"white",background:"#1874CD"});
    });
    hand_in.mouseout(function(){
        $(this).css({color:"#1874CD",background:"lightgrey"});
    });



function test2(){

	$(".question").each(function(){
		var isClicked="0";
		$(this).find(".A").mouseover(function(){
			if(!(isClicked === "A")){
    			$(this).parents(".question").find(".A").css({background:"lightgrey"});
			}
		});

		$(this).find(".B").mouseover(function(){
			if(!(isClicked === "B")){
    			$(this).parents(".question").find(".B").css({background:"lightgrey"});
			}
		});

		$(this).find(".C").mouseover(function(){
			if(!(isClicked === "C")){
    			$(this).parents(".question").find(".C").css({background:"lightgrey"});
			}
		});

		$(this).find(".D").mouseover(function(){
			if(!(isClicked === "D")){
    			$(this).parents(".question").find(".D").css({background:"lightgrey"});
			}
		});


		$(this).find(".A").mouseout(function(){
			if(!(isClicked === "A")){
				$(this).parents(".question").find(".A").css({background:"white"});
			}
		});

		$(this).find(".B").mouseout(function(){
			if(!(isClicked === "B")){
    			$(this).parents(".question").find(".B").css({background:"white"});
			}
		});

		$(this).find(".C").mouseout(function(){
			if(!(isClicked === "C")){
				$(this).parents(".question").find(".C").css({background:"white"});
			}
		});

		$(this).find(".D").mouseout(function(){
			if(!(isClicked === "D")){
				$(this).parents(".question").find(".D").css({background:"white"});
			}
		});

		$(this).find(".A").click(function(){
			if(!(isClicked === "0")){
				$(this).parents(".question").find("."+isClicked).css({background:"white"});
			}
			isClicked = "A";
			$(this).parents(".question").find(".A").css({background:"lightgrey"});
			$(this).parents(".question").find(".A input").prop("checked",true);
		});

		$(this).find(".B").click(function(){
			if(!(isClicked === "0")){
			$(this).parents(".question").find("."+isClicked).css({background:"white"});
			}
			isClicked = "B";
			$(this).parents(".question").find(".B").css({background:"lightgrey"});
			$(this).parents(".question").find(".B input").prop("checked",true);
		});

		$(this).find(".C").click(function(){
			if(!(isClicked === "0")){
			$(this).parents(".question").find("."+isClicked).css({background:"white"});
			}
			isClicked = "C";
			$(this).parents(".question").find(".C").css({background:"lightgrey"});
			$(this).parents(".question").find(".C input").prop("checked",true);
		});

		$(this).find(".D").click(function(){
			if(!(isClicked === "0")){
			$(this).parents(".question").find("."+isClicked).css({background:"white"});
			}
			isClicked = "D";
			$(this).parents(".question").find(".D").css({background:"lightgrey"});
			$(this).parents(".question").find(".D input").prop("checked",true);
		});
	});


        hand_in.click(function(){

            $(".right_answer").each(function(answer_idx){
                $(this).css("display","inline");
                var rs_num = $(this).find("div").first().text().charCodeAt(0)-65;
                $(this).parents(".question").find("input").each(function(idx,option){
                    if($(option).prop("checked")){
                        var rs = $("#result_table").find("[name="+(answer_idx+1)+"]");
                        rs.removeClass("label-default");
                        var sub_id = option.name;
                        var flag ;
                        if(idx===rs_num){
                            rs.addClass("label-success");
                            flag = true;
                        }else{
                            rs.addClass("label-danger");
                            flag = false;
                        }
                        $.get("http://testtiweb.appspot.com/record/"+sub_id,"flag="+flag,function(data){});
                    }
                });
            });
        });
}

setTimeout(function(){test2();
    },3000);
setTimeout(function(){    MathJax.Hub.Queue(["Typeset",MathJax.Hub]); },2500);
//===========================================================

});
