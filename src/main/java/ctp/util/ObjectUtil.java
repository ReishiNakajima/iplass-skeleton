package ctp.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

import org.iplass.mtp.entity.Entity;
import org.iplass.mtp.entity.SelectValue;
import org.iplass.mtp.util.CollectionUtil;
import org.iplass.mtp.util.StringUtil;

/**
 * ユーティリティクラス<br>
 *
 * @author lis93e
 */
public final class ObjectUtil {

	/**
	 * コンストラクタ
	 */
	private ObjectUtil() {
	}

	/**
	 * 非空白チェック
	 *
	 * @param param 検証値
	 * @return 空白でない=true/空白=false
	 */
	public static boolean isNotBlank(Object param) {
		return !isBlank(param);
	}

	/**
	 * 空白チェック。
	 * <p>
	 * nullチェックのほかに下記をチェック
	 * </p>
	 * <nl>
	 * <li>String : nullまたはブランク = true
	 * <li>SelectValue : value値が nullまたはブランク
	 * <li>Boolean : trueでない
	 * <li>Entity : oidが設定されている。
	 * </nl>
	 *
	 * @param param チェックする値
	 * @return (空白=true/空白ではない=false)
	 */
	public static boolean isBlank(Object param) {
		if (param == null) {
			return true;
		}
		if (param instanceof String) {
			return StringUtil.isBlank((String) param);
		} else if (param instanceof SelectValue) {
			return StringUtil.isBlank(((SelectValue) param).getValue());
		} else if (param instanceof Boolean) {
			return !Boolean.TRUE.equals(param);
		} else if (param instanceof Entity) {
			return StringUtil.isBlank(((Entity) param).getOid());
		}
		return false;
	}

	/**
	 * オブジェクトクラスのものを各クラスにキャストするメソッド
	 *
	 * @param obj 変換前の値
	 * @param <T> 変換後のクラス
	 * @return 変換された値
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj) {
		return (T) obj;
	}

	/**
	 * 一致チェック
	 *
	 * @param object 対象項目
	 * @param other 比較項目
	 * @return 引数が相互に等しい場合は true、それ以外の場合は false
	 * @since 2015/02/12
	 */
	public static boolean equals(Object object, Object other) {
		// 完全一致またはnull一致
		if (object == other) {
			return true;
		}

		// 片方がnullの場合
		if (object == null || other == null) {
			if (object instanceof String || other instanceof String) {
				return equals((String) object, (String) other);
			}
			return false;
		}

		if (!object.getClass().equals(other.getClass())) {
			return false;
		} else if (object instanceof Entity) {
			return equals((Entity) object, (Entity) other);
		} else if (object instanceof String) {
			return equals((String) object, (String) other);
		} else if (object instanceof BigDecimal) {
			return equals((BigDecimal) object, (BigDecimal) other);
		} else {
			return Objects.equals(object, other);
		}
	}

	/**
	 * 浮動小数点型(BigDecimal)のベリファイチェック<br>
	 * equalsでは1.0と1が一致しないため。
	 *
	 * @param normal 通常
	 * @param verify ベリファイ
	 * @return boolean 引数が相互に等しい場合は true、それ以外の場合は false
	 */
	public static boolean equals(BigDecimal normal, BigDecimal verify) {
		if (normal == verify) {
			return true;
		} else {
			return normal != null && verify != null && normal.compareTo(verify) == 0;
		}
	}

	/**
	 * リファレンスの比較<br>
	 * Entityの一致チェック方法がoidチェックとなる。
	 *
	 * @param normal 通常
	 * @param verify ベリファイ
	 * @return boolean 引数が相互に等しい場合は true、それ以外の場合は false
	 */
	public static boolean equals(Entity normal, Entity verify) {
		if (normal == verify) {
			return true;
		} else if (normal == null || verify == null) {
			return false;
		} else {
			return normal.getOid() != null && normal.getOid().equals(verify.getOid());
		}
	}

	/**
	 * Stringの比較<br>
	 * ""とnullを一致させるため。
	 *
	 * @param normal 通常
	 * @param verify ベリファイ
	 * @return boolean 引数が相互に等しい場合は true、それ以外の場合は false
	 */
	public static boolean equals(String normal, String verify) {
		if (StringUtil.isBlank(normal) && StringUtil.isBlank(verify)) {
			return true;
		} else {
			return normal != null && normal.equals(verify);
		}

	}

	public static <T> Iterable<T> toIterable(T[] array) {
		return () -> new Iterator<T>() {
			private int i = 0;

			@Override
			public boolean hasNext() {
				return array != null && i < array.length;
			}

			@Override
			public T next() {
				return array[i++];
			}
		};
	}

	public static <T extends Comparable<? super T>> T max(T a, T b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}

		if (a.compareTo(b) > 0) {
			return a;
		}
		return b;
	}

	public static <T extends Comparable<? super T>> T min(T a, T b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}

		if (a.compareTo(b) > 0) {
			return b;
		}
		return a;
	}
	//
	// @SafeVarargs
	// public static <E> boolean isAnyBlank(E... elements) {
	// return Sets.mutable.of(elements).anySatisfy(ObjectUtil::isBlank);
	// }

	/**
	 * 第一引数の文字列に対し、第二引数のkey値を第二引数のvalue値へ置き換える。<br />
	 * 第一引数の文字列で、置き換えたい文字は以下のように設定してください。<br />
	 * <br />
	 * {第二引数のkey値}
	 *
	 * @param baseStr バインド前の文字列
	 * @param bindData バインド情報
	 * @return バインド後の文字列
	 */
	public static String replace(String baseStr, Map<String, String> bindData) {
	    if (StringUtil.isBlank(baseStr) || CollectionUtil.isEmpty(bindData)) {
	        return baseStr;
	    }
	    String result = baseStr;
	    for (Map.Entry<String, String> data : bindData.entrySet()) {
	        if (StringUtil.isNotBlank(data.getKey())) {
	            result = result.replaceAll("\\{" + data.getKey() + "\\}"
	                    , StringUtil.isNotBlank(data.getValue()) ? Matcher.quoteReplacement(data.getValue()) : "");
	        }
	    }
	    return result;
	}
}