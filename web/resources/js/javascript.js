function sifreCalisanEkle(){

    var sifre=prompt("Şifrenizi girin");
    if(sifre=='1234'){
        alert("Şifre Onaylandı");
        location="calisanEkle.xhtml";
    }else {
        alert("Şifre Yanlış");
    }
}

function sifreCalisanGuncelle() {
    var sifre=prompt("Şifrenizi girin");
    if(sifre=='1234'){
        alert("Şifre Onaylandı");
        location="calisanGuncelle.xhtml";
    }else {
        alert("Şifre Yanlış");
    }

}

function sifreCalisanSil() {
    var sifre=prompt("Şifrenizi girin");
    if(sifre=='1234'){
        alert("Şifre Onaylandı");
        location="calisanSil.xhtml";
    }else {
        alert("Şifre Yanlış");
    }
}

function sifreCalisanGoster() {
    var sifre=prompt("Şifrenizi girin");
    if(sifre=='1234'){
        alert("Şifre Onaylandı");
        location="calisanGoster.xhtml";
    }else {
        alert("Şifre Yanlış");
    }
}

function sifreyiKontrolEt(form) {

    if (form.password.value=='1234'){
        alert("Şifre Onaylandı");
        location='calisanEkle.xhtml';
    }else {
        alert("Şifre Yanlış");
    }
}