package cn.qingsong.car.keyboardlibrary.engine;

import static cn.qingsong.car.keyboardlibrary.engine.NumberType.*;

/**
 * 禁用返回键的逻辑：
 * 1. 第1位，武警、军队、新旧大使馆、民航类型；
 * 2. 第2位，民航类型；
 * 3. 第7位，新2017式大使馆、新旧领事馆类型；
 *
 * @author 陈哈哈 (yoojiachen@gmail.com)
 */
public class BackKeyTransformer extends LayoutMixer.AbstractTypedKeyTransformer {

    public BackKeyTransformer() {
        super(KeyType.FUNC_BACK);
    }

    @Override
    protected KeyEntry transform(Context ctx, KeyEntry key) {
        if (0 == ctx.selectIndex && ctx.numberType.isAnyOf(WJ2012, PLA2012, SHI2017, SHI2012, AVIATION)) {
            return KeyEntry.newOfSetEnable(key, false);
        } else if (1 == ctx.selectIndex && AVIATION.equals(ctx.numberType)) {
            return KeyEntry.newOfSetEnable(key, false);
        } else if (6 == ctx.selectIndex && ctx.numberType.isAnyOf(SHI2017, LING2018, LING2012)) {
            return KeyEntry.newOfSetEnable(key, false);
        } else {
            return key;
        }
    }
}
