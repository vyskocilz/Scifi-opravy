package oprava;

import base.EntityList;
import data.NovaOpravaAkce;
import data.OpravaData;
import data.OpravyData;
import server.Server;
import server.ServerUtils;

import java.util.ArrayList;
import java.util.List;

public class Opravy extends EntityList<Oprava> {

    @Override
    public void init() {
        createNewElement();
    }

    @Override
    public void sendElement(Oprava element) {
        if (Server.isRunning()) {
            ServerUtils.getClientGroup().broadcast(mapOpravaData(element));
        }
    }

    @Override
    public void sendElements() {
        if (Server.isRunning()) {
            List<OpravaData> opravy = new ArrayList<OpravaData>();
            for (Oprava zdroj : getList()) {
                opravy.add(mapOpravaData(zdroj));
            }
            ServerUtils.getClientGroup().broadcast(new OpravyData(opravy));
        }
    }

    @Override
    public Oprava createElements() {
        return new Oprava("Nová chyba");
    }

    private static OpravaData mapOpravaData(Oprava oprava) {
        return new OpravaData(oprava.getSysName(), oprava.getNazev(), oprava.getPopis(), oprava.isViditelnost(), oprava.isVystraha(), oprava.isVarovani(), oprava.isVyresena(), oprava.getCrossX(), oprava.getCrossY(), oprava.isShowCross(), oprava.isAktivni());
    }

    public void loadFromSave(List<Oprava> list) {
        setList(list);
    }

    public void doAction(NovaOpravaAkce akce) {
        for (Oprava oprava : getList()) {
            if (oprava.getKod() != null && oprava.getKod().equals(akce.getKod())) {
                if (oprava.isAktivni() && !oprava.isVyresena()) {
                    oprava.setViditelnost(true);
                }
            }
        }
    }
}
