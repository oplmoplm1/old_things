
$(document).ready(function(){

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
                                "</div></div>");
                    });

                }
            });
	var aSpan = $(".clockNum");
	var c=0;
    var isTimer=true;
	t=setInterval(getTimes, 1000);
	getTimes();
	function getTimes ()
	{
        c++;
        var timer = new Array(2);
        timer[0]=parseInt(c%3600/60);
        timer[1]=c%60;
		for (var i in timer) aSpan[i].innerHTML = timer[i].toString().replace(/^(\d)$/, "0$1");
	}

/*
 *
 *leftsideBar
 *
 */
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

    $("#handInTestPaperButton").mouseover(function(){
        $("#handInTestPaperButton").css({color:"white",background:"#1874CD"});
    });
    $("#handInTestPaperButton").mouseout(function(){
        $("#handInTestPaperButton").css({color:"#1874CD",background:"lightgrey"});
    });
        $("#handInTestPaperButton").click(function(){
        //add hand in here
    });
//===========================================================


/*
 *
 *testPaperPart
 *
 */

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
}
//	$(".question").each(function(){
//
//	});
//
//
//	$(".question").each(function(){
//
//	});
//
setTimeout(function(){test2();
    },1000);
//===========================================================

});