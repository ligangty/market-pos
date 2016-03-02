(function(win, $) {
  win.PersonFi.Messages = {
    "CREATION_FAILED": "创建失败，失败码为: %s",
    "ADDING_FAILED": "添加失败, 状态码为: %s",
    "ITEM_FETCH_FAILED": "项目获取失败",
    "BALANCE_INPUT_NEEDED": "必须输入金额！",
    "BALANCE_FORMAT_INVALID": "金额输入格式错误！",
    "ITEM_NAME_INPUT_NEEDED": "必须输入收入项目名称!",
    "ITEM_SAVE_SUCCESS": "已经成功保存收支项目信息!",
    "RECORD_SAVE_SUCCESS": "新增记录成功，点击这里查看<a href='%s/account_book.do'>该月账本!</a>",
    "CATEGORY_INIT_FAILED":"项目读取错误！",
    "CATEGORY_DELETE_SUCCESS":"项目删除成功！",
    "CATEGORY_UPDATE_SUCCESS":"项目修改成功！"
  };

  $.i18n.load(win.PersonFi.Messages);
})(window, jQuery);
