package library.module.dialog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



//@Target(ElementType.LOCAL_VARIABLE)
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.SOURCE)
public @interface ThemeAnno {



}
