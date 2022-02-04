package ctp.dao;

import java.util.List;

import org.iplass.mtp.ManagerLocator;
import org.iplass.mtp.entity.DeleteOption;
import org.iplass.mtp.entity.Entity;
import org.iplass.mtp.entity.EntityManager;
import org.iplass.mtp.entity.InsertOption;
import org.iplass.mtp.entity.LoadOption;
import org.iplass.mtp.entity.SearchOption;
import org.iplass.mtp.entity.SearchResult;
import org.iplass.mtp.entity.UpdateCondition;
import org.iplass.mtp.entity.UpdateCondition.UpdateValue;
import org.iplass.mtp.entity.UpdateOption;
import org.iplass.mtp.entity.ValidateResult;
import org.iplass.mtp.entity.query.Query;
import org.iplass.mtp.entity.query.Where;
import org.iplass.mtp.util.CollectionUtil;
import org.iplass.mtp.util.StringUtil;

import ctp.util.ObjectUtil;

//import jp.co.isid.etp.util.ObjectUtil;

/**
 * DAO抽象クラス
 * <p>
 * 継承にてloadできるクラスを固定する。<br>
 * また、合わせてコンストラクタにてそのクラスのdefinitionNameを引数とする。
 * </p>
 * <p>
 * これにより{@link EntityManager#load(String, String) load}の引数defName部位と
 * {@link EntityManager#searchEntity(Query) searchEntity}の {@link Query}で {@link Query#from(String)
 * from}の指定をDao側で行うことができる。
 * </p>
 *
 * @author lis93e
 * @param <E> Entityを継承したクラス
 */
public abstract class Dao<E extends Entity> {

  /** ドット */
  protected static final String DOT = ".";

  /** EntityManager */
  protected final EntityManager em = ManagerLocator.getInstance().getManager(EntityManager.class);
  /** Entity定義名 */
  protected final String defName;

  /** load時にreferenceを取得するためのOption */
  private static final LoadOption loadRefOption = new LoadOption(true, false);
  /** load時にreferenceを取得しないためのOption */
  private static final LoadOption nonLoadRefOption = new LoadOption(false, false);
  /** delete時に物理削除するOption */
  private static final DeleteOption purgeDelOption; static {
      purgeDelOption = new DeleteOption();
      purgeDelOption.setPurge(true);
  }
  /** delete時に論理削除するOption */
  private static final DeleteOption purgeDelOptionLogical; static {
      purgeDelOptionLogical = new DeleteOption();
      purgeDelOptionLogical.setPurge(false);
  }

    /**
     * コンストラクタ
     * <p>
     * EntityのdefinitionNameを引数とする。
     * </p>
     *
     * @param defName EntityのdefinitionName
     */
    protected Dao(String defName) {
        this.defName = defName;
    }

    /**
     * Insert処理
     *
     * @param entity insetしたいEntityデータ
     * @return oid insertしたEntityのoid
     */
    public String insert(E entity) {
        return em.insert(entity);
    }

    /**
     * 強制Insert処理
     * <p>
     * バリデーションチェック、リスナーは実行しない
     * </p>
     *
     * @param entity insetしたいEntityデータ
     * @return oid insertしたEntityのoid
     */
    public String forceInsert(E entity) {
        InsertOption option = new InsertOption();
        option.setNotifyListeners(false);
        option.setWithValidation(false);
        return em.insert(entity, option);
    }

    /**
     * Entity情報取得
     * <p>
     * defNameを指定することにより引数がOidだけとなる。 また別のEntityを取得することができなくなっている。
     * </p>
     * <p>
     * referenceの値も取得可能(oidなど)、不要な場合は{@link #loadNonRef(String)}を利用。
     * </p>
     *
     * @param oid Entityのoid
     * @param op Entityのload時のオプション
     * @return oidを元としたEntity
     */
    public E load(String oid, LoadOption op) {
        if (StringUtil.isBlank(oid)) {
            return null;
        }

        return ObjectUtil.cast(em.load(oid, this.defName, op));
    }

    /**
     * Entity情報取得
     * <p>
     * defNameを指定することにより引数がOidだけとなる。 また別のEntityを取得することができなくなっている。
     * </p>
     * <p>
     * referenceの値も不可能、必要な場合は{@link #load(String)}を利用。
     * </p>
     *
     * @param oid Entityのoid
     * @return oidを元としたEntity
     */
    public E load(String oid) {
        return load(oid, loadRefOption);

    }

    /**
     * Entity情報取得
     * <p>
     * defNameを指定することにより引数がOidだけとなる。 また別のEntityを取得することができなくなっている。
     * </p>
     * <p>
     * referenceの値も不可能、必要な場合は{@link #load(String)}を利用。
     * </p>
     *
     * @param oid Entityのoid
     * @return oidを元としたEntity
     */
    public E loadNonRef(String oid) {
        return load(oid, nonLoadRefOption);

    }

    /**
     * データの検索
     * <p>
     * Queryを元にデータを取得する。
     * </p>
     * <p>
     * 継承したそれぞれのDaoのコンストラクタで指定したEntityを取得する。
     * </p>
     *
     * @see Dao#Dao(String) コンストラクタ
     * @param query 検索条件
     * @return list 検索結果list&lt;E&gt;
     */
    protected List<E> search(Query query) {
        return em.<E> searchEntity(query.from(this.defName)).getList();
    }

    /**
     * データの検索
     * <p>
     * Queryを元にデータを取得する。
     * </p>
     * <p>
     * 継承したそれぞれのDaoのコンストラクタで指定したEntityを取得する。
     * </p>
     *
     * @see Dao#Dao(String) コンストラクタ
     * @param query 検索条件
     * @return list 検索結果list&lt;E&gt;
     */
    protected List<E> searchSelectAll(Query query) {
        return em.<E> searchEntity(query.selectAll(this.defName, false, false)).getList();
    }

    /**
     * データの検索
     * <p>
     * 条件にoidの一致などを利用するものを想定し、Listでなく一件のデータのみを取得する。
     * </p>
     * <p>
     * 複数のデータが結果としてある場合エラーとなることを想定し{@link Query#limit limit}を指定することはないので、 条件に合うデータが複数存在しないことは保障されない。
     * </p>
     * <p>
     * 必ず1データしかないようなものであればlimit(1)を複数存在する可能性があるならばlimit(2)を事前に指定すると処理が理想的。
     * </p>
     *
     * @param query 検索条件となるクエリ
     * @return 検索されたEntity
     */
    protected E searchOne(Query query) {
        List<E> list = search(query);
        if (CollectionUtil.isEmpty(list) || list.size() > 1) {
            return null;
        }
        return list.get(0);
    }

    /**
     * データの検索
     * <p>
     * 条件にoidの一致などを利用するものを想定し、Listでなく一件のデータのみを取得する。
     * </p>
     * <p>
     * 複数のデータが結果としてある場合エラーとなることを想定し{@link Query#limit limit}を指定することはないので、 条件に合うデータが複数存在しないことは保障されない。
     * </p>
     * <p>
     * 必ず1データしかないようなものであればlimit(1)を複数存在する可能性があるならばlimit(2)を事前に指定すると処理が理想的。
     * </p>
     *
     * @param query 検索条件となるクエリ
     * @return 検索されたEntity
     */
    protected E searchOneSelectAll(Query query) {
        List<E> list = searchSelectAll(query);
        if (CollectionUtil.isEmpty(list) || list.size() > 1) {
            return null;
        }
        return list.get(0);
    }

    /**
     * データの件数検索
     *
     * @param query 検索条件となるクエリ
     * @return count 検索条件と一致するEntity件数
     */
    public int count(Query query) {
        return em.count(query.from(this.defName));
    }

    /**
     * Update処理
     *
     * @param entity Entity
     * @param option updateOption
     */
    public void update(E entity, UpdateOption option) {

      if (ObjectUtil.isBlank(option) || ObjectUtil.isBlank(option.getUpdateProperties()) || option.getUpdateProperties().isEmpty()) {
        return;
      }

       em.update(entity, option);
    }

    /**
     * 削除処理本体
     * <p>
     * 引数によって処理物理削除どうかなどを変更する。
     * </p>
     *
     * @param entity 削除対象Entity
     * @param deleteOption 削除オプション
     */
    public void delete(E entity, DeleteOption deleteOption) {
        try {
            em.delete(entity, deleteOption);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * entityの物理削除
     *
     * @param entity 削除対象のEntity
     */
    public void delete(E entity) {
        delete(entity, purgeDelOption);
    }

    /**
     * entityの論理削除
     *
     * @param entity 削除対象のEntity
     */
    public void deleteLogical(E entity) {
        delete(entity, purgeDelOptionLogical);
    }

    /**
     * ロードとロック
     *
     * @param oid Entityのoid
     * @return loadしたEntity
     */
    public E loadAndLock(String oid) {
        return ObjectUtil.cast(em.loadAndLock(oid, this.defName, new LoadOption(false, false)));
    }

    /**
     * アップデート処理(条件指定) where条件を満たすEntityに対してlist内容の更新を行う
     *
     * @param list 更新対象と更新内容
     * @param where 条件
     * @return 更新件数
     */
    public int updateAll(List<UpdateValue> list, Where where) {
        UpdateCondition updateCondition = new UpdateCondition(this.defName, list, where);
        return em.updateAll(updateCondition);
    }

    /**
     * バリデーションチェック
     * <p>
     * 指定したプロパティのみ
     * </p>
     * @param entity チェック対象のEntity
     * @param properties チェック対象のプロパティ
     * @return チェック結果
     */
    public ValidateResult validate(E entity, List<String> properties) {
        return em.validate(entity, properties);
    }

    /**
     * バリデーションチェック
     * <p>
     * すべてのプロパティ
     * </p>
     * @param entity チェック対象のEntity
     * @return チェック結果
     */
    public ValidateResult validate(E entity) {
        return em.validate(entity);
    }

    /**
     * 検索を実行します(1件)
     *
     * @param query クエリ
     * @return Object[] 検索結果
     */
    protected Object[] searchOne2(Query query) {
        SearchResult<Object[]> result = this.em.search(query.from(this.defName));
        return result.getFirst();
    }

    /**
     * 検索を実行します
     *
     * @param query クエリ
     * @return List&lt;Object[]&gt; 検索結果
     */
    protected List<Object[]> search2(Query query) {
        SearchResult<Object[]> result = this.em.search(query.from(this.defName));
        return result.getList();
    }

    /**
     * 検索を実行します
     *
     * @param query クエリ
     * @return List&lt;E&gt; 検索結果
     */
    protected List<E> searchEntity(Query query) {
        SearchResult<E> result = this.em.searchEntity(query.from(this.defName));
        return result.getList();
    }

    /**
     * 検索を実行します
     *
     * @param query クエリ
     * @param option SearchOption
     * @return List&lt;E&gt; 検索結果
     */
    protected List<E> searchEntity(Query query, SearchOption option) {
        SearchResult<E> result = this.em.searchEntity(query.from(this.defName), option);
        return result.getList();
    }

    /**
     * 検索を実行します
     *
     * @param query クエリ
     * @return E 検索結果
     */
    protected E searchEntityOne(Query query) {
        SearchResult<E> result = this.em.searchEntity(query.from(this.defName));
        return result.getFirst();
    }

    /**
     * 検索を実行します
     *
     * @param query クエリ
     * @param option SearchOption
     * @return E 検索結果
     */
    protected E searchEntityOne(Query query, SearchOption option) {
        SearchResult<E> result = this.em.searchEntity(query.from(this.defName), option);
        return result.getFirst();
    }
}
