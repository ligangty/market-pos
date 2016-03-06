(function(win, $) {
  win.Pos = {
    contextPath:""
  };

  win.Pos.init = function(contextPath){
    win.Pos.contextPath = contextPath;
    $('#submitBarCode').click(win.Pos.sendBarCode);
  }

  win.Pos.Contants = {
    MSG_EFFECT_TIME: 500,
    MSG_DELAY_TIME: 2000,
  };

  win.Pos.getResult = function(){
    $.ajax({
        url: win.Pos.contextPath+"/js/result.json",
        type: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      }).success(function(data) {
        console.log(data);
      }).error(function(xhr, status) {
        console.log($.i18n._("ITEM_FETCH_FAILED", status));
      });
  }

  win.Pos.sendBarCode = function(){
    var barCodes = $('#barCode').val();
    $.ajax({
        url: win.Pos.contextPath + "/products/barcode",
        type: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify(barCodes)
    }).success(function(data){
        console.log(data);
    }).error(function(xhr,status){
        console.log(status);
    });
  }

})(window, jQuery);