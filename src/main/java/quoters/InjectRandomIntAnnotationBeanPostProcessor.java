package quoters;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * @author Alexander Dudkin
 */
public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    @SneakyThrows
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                Random random = new Random();
                field.setAccessible(true);
                field.set(bean, min + random.nextInt(max - min));
                return bean;
            }
        }
        return bean;
    }

}
