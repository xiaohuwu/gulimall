import com.ktb.gulimall.product.GulimallProductApplication;
import com.ktb.gulimall.product.entity.PmsBrandEntity;
import com.ktb.gulimall.product.service.PmsBrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= GulimallProductApplication.class)
//注意 这里面的类名和 启动类不能一样，不然会冲突
public class GulimallProductApplicationTest {

     @Autowired
     PmsBrandService brandService;

//    @Autowired
//    ApplicationContext applicationContext;

    @Test
    public void testProduct(){

//        System.out.println("===============>"+Arrays.asList(applicationContext.getBeanDefinitionNames()));

        PmsBrandEntity pmsBrandEntity = new PmsBrandEntity();
        pmsBrandEntity.setName("华为");
        brandService.save(pmsBrandEntity);
        PmsBrandEntity byId = brandService.getById(pmsBrandEntity.getBrandId());
        System.out.println("byId = " + byId);
    }

}
