package com.meliksah.banka.app.transactional.controller;

import com.meliksah.banka.app.transactional.service.NonTransactionalService;
import com.meliksah.banka.app.transactional.service.TransactionalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactional")
public class TransactionController {

    private final TransactionalService transactionalService;
    private final NonTransactionalService nonTransactionalService;

    /*
    jparepo'daki save methodu süresince transaction açıldı ve commit yapıp kapandı
    */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts1")
    public void ts1(){
        nonTransactionalService.save();
    }

    /*
    transactionalservice'deki save methodu süresince transaction açıldı ve commit yapıp kapandı
    */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts2")
    public void ts2(){
        transactionalService.save();
    }

    /*
    transactionalservice'deki saveT2N methodu içinde nontransactional bir methoda bağlanmasına rağmen
    yine de saveT2N süresince transaction açıldı ve commit yapıp kapandı
    */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts3")
    public void ts3(){
        transactionalService.saveT2N();
    }


    /*
    nontransaction method'dan transactional bir methoda bağlanıyor.
    jparepo'daki save methodu süresi boyunca transaction açıldı ve commit yapıp kapandı.
    transactional.save methodunun başından sonuna kadar olan süre boyunca tekrar transaction açıldı ve commit yapıp kapandı.
     */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts4")
    public void ts4(){
        nonTransactionalService.saveT2N();
    }

    /*
    transactional method'dan transactional bir methoda bağlanıyor.
    transactionalService'deki saveT2T methodu süresi boyunca transaction açıldı ve commit yapıp kapandı.
     */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts5")
    public void ts5(){
        transactionalService.saveT2T();
    }

    /*,
    * transactional bir methodda hata alıyoruz.saveButError method başında trasanction açılıyor
    * sonra throw atılan yerde rollback atılıyor.saveButError method sonunda da transaction kapatılıyor.
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts6")
    public void ts6(){
        transactionalService.saveButError();
    }

    /*
    * nontransactional bir methodda hata alıyoruz.jparepo'daki save methodu süresi boyunca
    * transactional açıldı ve commit yapıp kapandı. sonra hata atıldığı için hata transaction'u rollback yapmadı.
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts7")
    public void ts7(){
        nonTransactionalService.saveButError();
    }

    /*
     * transactional bir methoddan aynı class'taki requiresnew typle'lı bir transaction methoduna gidiyoruz.
     * transactionalService deki saveT2RN methodu başında transaction açtı.
     * transactionalservice deki saveRN methoduna geçerken farklı bir transaction açması gerekirdi ama
     * aynı bean içerisinde olduğu için farklı bir transaction açmadı.
     * transactionalService deki saveT2RN methodu sonunda commit yapıldı ve transaction kapandı.
     * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts8")
    public void ts8(){
        transactionalService.saveT2RN();
    }

    /*
     * transactional bir methoddan farklı class'taki requiresnew typle'lı bir transaction methoduna gidiyoruz.
     * transactionalService deki saveT2RNWithDifferentBean methodu başında transaction açtı.
     * transactionalservice2 deki saveRN methoduna geçerken eski transaction'u askıya aldı ve geçilen methodun başında
     * yeni bir transaction açtı.transactionalservice2 deki saveRN methodu bitince yeni transaction commit yapıldı ve kapandı.
     * transactionalService deki saveT2RNWithDifferentBean methodu sonunda da askıya alınan eski transaction commit yapıldı ve kapandı.
     * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts9")
    public void ts9(){
        transactionalService.saveT2RNWithDifferentBean();
    }


    /*
    * saveT2RNButError methodunun başında transaction açıldı.for döngüsü ile farklı class'taki requiresnew type'lı bir transaction methoduna
    * gidiyor.Her gittiğinde saveT2RNButError methodunun başında açtığı transaction'u askıya alıp yeni bir transaction açıyor tekrar for döngüsüne
    * döndüğünde requiresnew'li methodun açtığı transactionu commitleyip kapatıyor ve saveT2RNButError methodunun açtığı transaction tekrar aktif hale geliyor.
    * i==7 olduğunda hata attı saveT2RNButError methodunun içindeki catch bloğuna düştü ve for döngüsü bitti.i==8 ve i==9 olduğu requiresnew'li methodlar çalışmadı
    * sonra da saveT2RNButError methodunun açtığı transaction commit edildi ve kapandı.
    *
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts10")
    public void ts10(){
        transactionalService.saveT2RNButError();
    }

    /**
     *nonetransactionalservicedeki saveMandatory methodu çağrıldığı zaman içindeki transactionalservice.saveMandatory() methodu çağrılınca hata aldık
     * propagation'u mandatory. çünkü Mandatory çağrıldığı yerde transactional olmadığı için hata verir.
     *
     */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts11")
    public void ts11(){
        nonTransactionalService.saveN2M();
    }

    /**
     *transactionalservicedeki saveT2M methodu çağrıldığı zaman transactional açıldı.sonra transactionalservice2.saveMandatory methodu çağrılınca
     * saveT2M methodunun açtığı transactional devam etti. saveT2M methodu bitince commit yapıldı ve saveT2M methodunun açtığı transactional kapandı.
     *
     */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts12")
    public void ts12(){
        transactionalService.saveT2M();
    }

    /*
    * transactionalService.saveT2S methodu başlayınca transaction açılacak.sonra bu methodun içindeki transactionalService2.saveSupport methodu çağrılınca
    * saveT2S methodunun açtığı transaction devam edecek.sonra saveT2S methodunun sonunda saveT2S methodunun açtığı transaction commit edilip kapanacak.
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts13")
    public void ts13(){
        transactionalService.saveT2S();
    }

    /*
     * nonTransactionalService.saveT2S methodu içinde jparepo'daki save methodu boyunca transaction açılacak ve kapanacak
     * sonra bu methodun içindeki transactionalService.saveSupport methodu çağrılınca hali hazırda bir transaction olmadığı için
     * transaction olmadan çalışmaya başlayacak mandatory gibi hata vermeyecek.sonra transactionalService.saveSupport methodu içindeki jparepo'nun
     * save methodu boyunca transaction açılacak ve commit edilip kapanacak.
     * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts14")
    public void ts14(){
        nonTransactionalService.saveN2S();
    }

    /*
    * transactionalService.doSomething methodu ve bağlandığı findById methodu transactional propagation.NOT_SUPPORTED ile çalışıyor
    *  o yüzden bu methodların çalışması için transactional açılmıyor.
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts15")
    public void ts15(){
        transactionalService.doSomething();
    }

    /*
    * propagation.NESTED Hibernate JPA'da desteklenmiyor.
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts16")
    public void ts16(){
        transactionalService.saveNested();
    }

    /*
    * nonetransactionalservice içinde dosomething methodu ile toplu işlemler yapıldı.toplu findbyıd en hızlı bu şekilde oluyor ts15'deki yöntemden de hızlı oluyor.
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts17")
    public void ts17(){
        nonTransactionalService.doSomething();
    }

    /*
     * saveT2RNButError methodunun başında transaction açıldı.for döngüsü ile farklı class'taki requires type'lı bir transaction methoduna
     * gidiyor.Her gittiğinde saveT2RNButError methodunun başında açtığı transactionu kullanıyor.i==7 olduğunda da ana transaction'u kullandığı için
     * ana transaction patlıyor. sonra saveT2RNButError methodunun açtığı transaction rollback edilip kapanıyor.
     *
     * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts18")
    public void ts18(){
        transactionalService.saveT2RNButError2();
    }

    /*
    * doSomethingWithNewTransaction methodu başında transaction açılıyor. Sonra findByIdWithNewTransaction methoduna
    * her gittiğinde yeni bir transaction açılıyor ve commitlenip kapatılıyor. Sonra doSomethingWithNewTransaction methodunda
    * açılan transaction commit edilip kapatılıyor.
    *
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts19")
    public void ts19(){
        transactionalService.doSomethingWithNewTransaction();
    }

    /*
    * transactional olmayan saveNever methodundan yine transactional olmayan saveNon2Never methodu çağrılınca herhangi bir şekilde
    * transaction açılıp kapatılmadı ve hata alınmadı.
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts20")
    public void ts20(){
        nonTransactionalService.saveNever();
    }

    /*
    * transactional olan saveNeverTransactional methodu başında transaction açılıyor. saveT2Never methodu çağrılınca aktif bir transaction
    * olduğu için hata atıyor.saveNeverTransactional nun açtığı transaction rollback edip kapatılıyor.
    * */
    @Operation(tags = "Transaction Controller")
    @PostMapping("/ts21")
    public void ts21(){
        transactionalService.saveNeverTransactional();
    }

}
