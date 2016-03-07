(function(win, $) {
  win.Pos = {
    contextPath:""
  };

  win.Pos.init = function(contextPath){
    win.Pos.contextPath = contextPath;
    $('#barCodeForm').submit(function(event){
            event.preventDefault();
    });
    $('#submitBarCode').click(win.Pos.processOrder);
  }

  win.Pos.Contants = {
    MSG_EFFECT_TIME: 500,
    MSG_DELAY_TIME: 2000,
  };

  win.Pos.processOrder = function(){
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

        // display common products
        var products = data.products;
        var ui_prodlist = "";
        $.each(products, function(index, elem){
            var ui_proditem = "<li>名称："+elem.name+"，数量："+elem.number+elem.unit+"，单价："+elem.price+"(元)，小计："+elem.total+"(元)";
            if(elem.save){
              ui_proditem = ui_proditem+"，节省"+elem.save+"(元)";
            }
            ui_proditem = ui_proditem+"</li>";
            ui_prodlist = ui_prodlist+ui_proditem;
        });

        $("#productsList").html(ui_prodlist);

        // display price off products
        var priceOffs = data.priceOff;
        if(priceOffs && priceOffs!=="" && priceOffs.length!==0){
            var ui_priceOffHtml = "";
            $.each(priceOffs, function(index, elem){
                ui_priceOffHtml = ui_priceOffHtml + "<p>----------------------</p><p>" + elem.name + "：</p>"
                var ui_priceOffSingleUl = $("<ul></ul>");
                var ui_priceofflist = "";
                $.each(elem.products, function(i, prod){
                    var ui_priceoffitem = "<li>名称："+prod.name+"，数量："+prod.giveNumber+prod.unit+"</li>";
                    ui_priceofflist = ui_priceofflist+ui_priceoffitem;
                });
                ui_priceOffSingleUl.html(ui_priceofflist);
                ui_priceOffHtml = ui_priceOffHtml + ui_priceOffSingleUl.html();
            });

            $("#priceOffResult").html(ui_priceOffHtml).show();
        }else{
            $("#priceOffResult").hide();
        }

        // display total price and total save
        var ui_showTotal = "<p>总计："+data.total+"(元)</p><p>节省："+data.totalSave+"(元)</p>";
        $("#showTotal").html(ui_showTotal);

    }).error(function(xhr,status){
        console.log(status);
    });

    var barCodes = $('#barCode').val("");
  }

})(window, jQuery);
