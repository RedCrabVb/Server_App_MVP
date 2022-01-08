$(document).ready(function(){
    $("#addCaseTest").click(function(){
         var $div = $('div[id^="testNumber"]:last');

         var num = parseInt( $div.prop("id").match(/\d+/g), 10 ) +1;

         var $klon = $div.clone().prop('id', 'testNumber'+num );
         $klon.children("#qiText").text("Вопрос №" + num);

         console.log("#nameQuestion"+(num-1))
         $klon.children("#question"+(num-1)).prop('id', 'question'+num).prop('name', 'nameQ_'+num).val("");
         $klon.children("#response"+(num-1)).prop('id', 'response'+num).prop('name', 'nameR_'+num).val("");
         $klon.children("#comment"+(num-1)).prop('id', 'comment'+num).prop('name', 'nameC_'+num).val("");

         $klon.appendTo("#test")
    });
});