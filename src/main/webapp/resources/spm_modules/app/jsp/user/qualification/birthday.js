/*出生年月日下拉列表
年的下拉列表id定义为：aa_bb_cc格式，bb是月下拉列表id, cc是日下拉列表id
如需预定义年月日---在年select上加一个pv="2015_12_3"
*/
var birth = {
    G: function(id){
        return typeof (id) == "string" ? document.getElementById(id) : id;
    },
    So: function (o) {
        return o.options[o.selectedIndex];
    },
    Slcted: function (slct, v, vt) {
        for (var i = 0; i < slct.length; i++) {
            if ((vt ? slct.options[i].value : slct.options[i].text) == v) {
                slct.options[i].selected = true;
                break;
            }
        }
    },
    AddEvent: function (obj, ev, fn, arg) {
        obj = this.G(obj);
        var Ehd = fn;
        if (arg) {
            Ehd = function (ev) { fn(ev, arg) };
        }
        
        if (window.addEventListener) {
            obj.addEventListener(ev, Ehd, false);
        }
        else if (window.attachEvent) {
            obj.attachEvent("on" + ev, Ehd);
        }
        else {
            obj["on" + ev] = Ehd;
        }
    },
    init: function(id) {
        var oY = this.G(id);
        var oM = this.G( id.split('_')[1] );
        var oD = this.G( id.split('_')[2] );                
        var curY = (new Date() ).getFullYear();
        var pv =  oY.getAttribute('pv'); /*上次已经选中的年月日*/
        if (pv) {
            var py = pv.split('_')[0];
            var pm = pv.split('_')[1];
            var pd = pv.split('_')[2];
        }

        for(var i = 0; i <= curY - 1930; i++) {
            oY.options.add(new Option(1930 + i, 1930 + i));            
        }

        for(i  = 0; i < 12; i++) {
            oM.options.add(new Option(i + 1, i + 1));            
        }

        if (py) {this.Slcted(oY, py, true);} /*设置预设的年份*/
        if (pm) {
            /*设置预设的月份和日期*/
            this.Slcted(oM, pm, true);
            birth.gd([oY, oM, oD]);
            this.Slcted(oD, pd, true);
        }         
        
        this.AddEvent(oY, 'change', birth.cm, [oY, oM, oD]);
        this.AddEvent(oM, 'change', birth.cd, [oY, oM, oD]);
    },
    /*改变年份时改变月份和日期*/
    cm: function (e, ars){
        birth.Slcted(ars[1], '0', true);
        ars[2].innerHTML = '';
        ars[2].options.add(new Option('请选择', 0));
    },
    /*改变月份时改变日期*/
    cd: function(e, ars){
        ars[2].innerHTML = '';
        ars[2].options.add(new Option('请选择', 0));
        birth.gd(ars);
    },
    /*生成日期下拉列表*/
    gd: function(ars){

        var yy, mm, t, d;
        yy = this.So( ars[0] ).value;
        mm = this.So( ars[1] ).value;
        t = ((yy % 4)==0) && ((yy % 100)!=0) || ((yy % 400)==0); /*是否闰年*/
        
        switch( parseInt(mm) ){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                d = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                d = 30;
                break;
            case 2:
                d = t ? 29 : 28;
        }

        for(var j = 0; j < d; j++) {
            ars[2].options.add(new Option(j + 1, j + 1));
        }
    }
};