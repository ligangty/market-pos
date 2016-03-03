(function(win, $) {
  win.Pos = {};

  win.Pos.Contants = {
    MSG_EFFECT_TIME: 500,
    MSG_DELAY_TIME: 2000,
  };

  win.Pos.getResult = function(contextPath){
    $.ajax({
        url: contextPath+"/js/result.json",
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

})(window, jQuery);