(function(win, $) {
  win.Pos = {};

  win.Pos.Contants = {
    MSG_EFFECT_TIME: 500,
    MSG_DELAY_TIME: 2000,
  };

  win.Pos.Util = {
    getOrChgMessageBox: function(id, msg) {
      var id$ = '#' + id;
      var spnid$ = '#' + id + '-spnMessage';
      if ($(id$)[0]) {
        $(spnid$).html(msg);
      } else {
        return $('<div class="eInfo" id="' + id
                + '" style="display: none;">&nbsp;<b class="icoInfo">'
                + '&nbsp;&nbsp;&nbsp;&nbsp;</b><span id="' + id
                + '-spnMessage">' + msg + '</span></div>')[0].outerHTML;
      }
    },
    equalsBetweenNumberAndString: function(value1, value2) {
      if ((typeof (value1) === "string" || typeof (value1) === "number")
              && (typeof (value2) === "string" || typeof (value2) === "number")) {
        return (value1.toString()===value2.toString());
      }
      return false;
    }

  };

  win.Pos.CommonUI = {
    messageBoxEffect: function(messageBoxId, insertBeforeId, msg) {
      var showMessegeBox = $("#" + messageBoxId);
      if (showMessegeBox[0]) {
        Pos.Util.getOrChgMessageBox(messageBoxId, msg);
        showMessegeBox.slideToggle(Pos.Contants.MSG_EFFECT_TIME).delay(
                Pos.Contants.MSG_DELAY_TIME).slideToggle(
                Pos.Contants.MSG_EFFECT_TIME);
      } else {
        $("#" + insertBeforeId).before(
                $(Pos.Util.getOrChgMessageBox(messageBoxId, msg)));
        $("#" + messageBoxId).slideToggle(Pos.Contants.MSG_EFFECT_TIME)
                .delay(Pos.Contants.MSG_DELAY_TIME).slideToggle(
                        Pos.Contants.MSG_EFFECT_TIME);
      }
    }
  };
})(window, jQuery);