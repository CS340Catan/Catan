// STUDENT-CORE-BEGIN
// DO NOT EDIT THIS FILE

// Use this to override the reaction to submitting a form
function override_submit(jquerySelector, success, failure){
    $(jquerySelector).submit(function( event ) {
        event.preventDefault();
        event.stopImmediatePropagation();
        var obj = {}
        $.ajax({
            type: 'POST',
            url:$(this).attr('action'),
            data: $(this).serialize()
        })
            .done(success)
            .error(failure)
        return false;
    });
}

function ajax_submit(jquerySelector, success, failure){
    $(jquerySelector).submit(function( event ) {
      event.preventDefault();
      event.stopImmediatePropagation();
      data = $.extend.apply({},$(jquerySelector).serializeArray().map(function (input){console.log(input); var o = {}; o[input.name] = input.value; return o}))
      console.log(data)
      $.ajax({
            type: 'POST',
            url:$(this).attr('action'),
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify($.extend.apply({},$(jquerySelector).serializeArray().map(function (input){console.log(input); var o = {}; o[input.name] = input.value; return o})))
        })
        .done(success)
        .error(failure)
        return false;
    });
}


