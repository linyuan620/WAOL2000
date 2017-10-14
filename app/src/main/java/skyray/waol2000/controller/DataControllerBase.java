package skyray.waol2000.controller;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import skyray.waol2000.datamodel.IEqualble;


/**
 * Created by PengJianbo on 2017/9/1.
 */

public abstract class DataControllerBase<T extends SugarRecord & IEqualble> {
    protected Class cc = null;
    protected String order = "";

    public List<T> getAllInfos() {
        return allInfos;
    }

    private void loadAllInfos() {
        allInfos = SugarRecord.listAll(cc, order);
        if (allInfos == null) {
            allInfos = new ArrayList<>();
        }
    }

    protected abstract void InitClass();

    public DataControllerBase() {
        InitClass();
        loadAllInfos();
    }

    protected List<T> allInfos = new ArrayList<>();

    protected T getExistAV(T av) {
        T exist = null;
        if (av != null) {
            if (allInfos != null && allInfos.size() > 0) {
                for (int i = 0; i < allInfos.size(); i++) {
                    if (allInfos.get(i).isEqual(av)) {
                        exist = allInfos.get(i);
                        break;
                    }
                }
            }
        }
        return exist;
    }

    protected abstract void updateItem(T exist, T av);

    /**
     * 添加或修改数据
     *
     * @param av
     */
    public void updateInfo(T av) {
        if (av != null) {
            T exist = getExistAV(av);
            if (exist == null) {
                av.save();
                allInfos.add(av);
            } else {
                if (av != null) {
                    updateItem(exist, av);
                    exist.save();
                }
            }
        }
    }

    /**
     * 删除数据
     *
     * @param av
     */
    public void deleteInfo(T av) {
        if (av != null) {
            T exist = getExistAV(av);
            if (exist != null) {
                av.delete();
                allInfos.remove(exist);
            }
        }
    }

    public T getItemByID(int id) {
        T t = null;
        if (allInfos != null) {
            for (int i = 0; i < allInfos.size(); i++) {
                if (allInfos.get(i).getId().intValue() == id) {
                    t = allInfos.get(i);
                    break;
                }
            }
        }
        return t;
    }
}
