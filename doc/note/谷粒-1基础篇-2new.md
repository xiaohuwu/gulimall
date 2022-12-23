- 
- 笔记-基础篇-1(P1-P28)：[https://blog.csdn.net/hancoder/article/details/106922139](https://blog.csdn.net/hancoder/article/details/106922139)
- 笔记-基础篇-2(P28-P100)：[https://blog.csdn.net/hancoder/article/details/107612619](https://blog.csdn.net/hancoder/article/details/107612619)
- 笔记-高级篇(P340)：[https://blog.csdn.net/hancoder/article/details/107612746](https://blog.csdn.net/hancoder/article/details/107612746)
- 笔记-vue：[https://blog.csdn.net/hancoder/article/details/107007605](https://blog.csdn.net/hancoder/article/details/107007605)
- 笔记-elastic search、上架、检索：[https://blog.csdn.net/hancoder/article/details/113922398](https://blog.csdn.net/hancoder/article/details/113922398)
- 笔记-认证服务：[https://blog.csdn.net/hancoder/article/details/114242184](https://blog.csdn.net/hancoder/article/details/114242184)
- 笔记-分布式锁与缓存：[https://blog.csdn.net/hancoder/article/details/114004280](https://blog.csdn.net/hancoder/article/details/114004280)
- 笔记-集群篇：[https://blog.csdn.net/hancoder/article/details/107612802](https://blog.csdn.net/hancoder/article/details/107612802)
- springcloud笔记：[https://blog.csdn.net/hancoder/article/details/109063671](https://blog.csdn.net/hancoder/article/details/109063671)
- 笔记版本说明：2020年提供过笔记文档，但只有P1-P50的内容，2021年整理了P340的内容。请点击标题下面分栏查看系列笔记
- 声明：

  - 可以白嫖，但请勿转载发布，笔记手打不易
  - 本系列笔记不断迭代优化，csdn：hancoder上是最新版内容，10W字都是在csdn免费开放观看的。
  - 离线md笔记文件获取方式见文末。2021-3版本的md笔记打完压缩包共500k（云图床），包括本项目笔记，还有cloud、docker、mybatis-plus、rabbitMQ等个人相关笔记
- sql：[https://github.com/FermHan/gulimall/sql文件](https://github.com/FermHan/gulimall/sql文件)
- 本项目其他笔记见专栏：[https://blog.csdn.net/hancoder/category_10822407.html](https://blog.csdn.net/hancoder/category_10822407.html)




基础篇文档太长，就分P了吧

> 本篇2W字，请直接ctrl+F搜索内容



## 15. 删除分类

P49。删除前提：没有子菜单、没有被其他菜单引用

- 使用 render-content，渲染函数
- 使用 scoped slot：https://cn.vuejs.org/v2/guide/components-slots.html

> 可以通过两种方法进行树节点内容的自定义：`render-content`和 scoped slot。
>
> - 使用`render-content`指定渲染函数，该函数返回需要的节点区内容即可。渲染函数的用法请参考 Vue 文档。
> - 使用 `scoped slot` 会传入两个参数`node`和`data`，分别表示当前节点的 Node 对象和当前节点的数据。
> - 注意：由于 jsfiddle 不支持 JSX 语法，所以`render-content`示例在 jsfiddle 中无法运行。但是在实际的项目中，只要正确地配置了相关依赖，就可以正常运行。

render-content：

```html
 <el-tree
      :data="data"
      show-checkbox
      node-key="id"
      default-expand-all
      :expand-on-click-node="false"
      :render-content="renderContent"> // 对应到函数，去得到数据并渲染
     
     匹配到了
     
      renderContent(h, { node, data, store }) {
        return ( // 返回要显示的dom元素
          <span class="custom-tree-node">
            <span>{node.label}</span>
            <span>
              <el-button size="mini" type="text" on-click={ () => this.append(data) }>Append</el-button>
              <el-button size="mini" type="text" on-click={ () => this.remove(node, data) }>Delete</el-button>
            </span>
          </span>);
      }
```

 scoped slot（插槽）：在el-tree标签里把内容写到span标签栏里即可

#### ==node与data==

在element-ui的tree中，有2个非常重要的属性

- node代表当前结点（是否展开等信息，element-ui自带属性），
- data是结点数据，是自己的数据。
- data从哪里来：前面ajax发送请求，拿到data，赋值给menus属性，而menus属性绑定到标签的data属性。而node是ui的默认规则

> 注意下面的代码不要直接复制，我添加了一些自己的注释

```html
<div class="block">
    <p>使用 scoped slot</p>
    <el-tree
      :data="menus"
      show-checkbox
      node-key="id"
      default-expand-all
      :expand-on-click-node="false">
        传入了2个参数，node代表当前结点（是否展开等信息），data是结点数据。
        data从哪里来：前面ajax发送请求，拿到data，赋值给menus属性，而menus属性绑定到标签的data属性。
        而node是ui的默认规则
      <span class="custom-tree-node" slot-scope="{ node, data }">
          结点的标签
        <span>{{ node.label }}</span>
          结点后面跟的2个按钮
        <span>
          <el-button
            type="text"
            size="mini"
            @click="() => append(data)">
            Append
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="() => remove(node, data)">
            Delete
          </el-button>
        </span>
      </span>
    </el-tree>
```

##### 初始化分类

页面加载完后，自动调用

```js
created() {// 生命周期
    this.getMenus();// 会设置"menus"变量的值
},
    
    
    注意到tree标签上有一个
    <el-tree
      :data="menus"
    这样就自动注入到data上了
```

该方法用于获取到ajax数据后设置到对应的tree属性上

```js
methods: {
    getMenus() {
      this.$http({ // http://localhost:10000/renren-fast/product/category/list/tree
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get"
      })
        .then(({ data }) => { // success
          this.menus = data.data; // 数组内容，把数据给menus，就是给了vue实例，最后绑定到视图上
        }) //fail
        .catch(() => {});
    },
也就是说，获取到数据后绑定到menus上 
而<el-tree
      :data="menus"
所以数据绑定好了
层级怎么体现的：后端返回的时候children就封装好了，因为ui-tree是按这个属性来的
java后端的实体类CategoryEntity有属性
private List<CategoryEntity> children;
该属性不是数据库现有的，而是在后端根据数据库信息现封装好的
```

##### 初始化分类的controller

返回的时候已经设置好了child属性，所以前端可以直接渲染

```java
/**
     * 查出所有分类 以及子分类，以树形结构组装起来
     注意这个方法的递归调用要多读一读，结合lambda确实对写代码思维有提高
     */
@RequestMapping("/list/tree")
public R list(){
    List<CategoryEntity> entities = categoryService.listWithTree();
    // 筛选出所有一级分类
    List<CategoryEntity> level1Menus = entities.stream().
        filter((categoryEntity) -> categoryEntity.getParentCid() == 0)
        .map((menu) -> { 
            //  递归设置// menu代表要求的root
            menu.setChildren(getChildrens(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
        return (menu1.getSort() == null? 0 : menu1.getSort()) - (menu2.getSort() == null? 0 : menu2.getSort());
    })
        .collect(Collectors.toList());
    return R.ok().put("data", level1Menus);
}
/**
     * 递归找所有的子菜单、中途要排序
     */
private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all){
    List<CategoryEntity> children = all.stream().filter(categoryEntity ->
                                                        categoryEntity.getParentCid() == root.getCatId()
                                                       ).map(categoryEntity -> {
        categoryEntity.setChildren(getChildrens(categoryEntity, all));
        return categoryEntity;
    }).sorted((menu1,menu2) -> {
        return (menu1.getSort() == null? 0 : menu1.getSort()) - (menu2.getSort() == null? 0 : menu2.getSort());
    }).collect(Collectors.toList());
    return children;
}
```

> 注：lambda表达式中虽然叫map函数，但是并不是java里的map。如果想转成map(key1函数,key2函数,(k1,k2)->k2;) 

重写2个按钮的事件，发送ajax操作数据库

要调整按钮的显示情况，用v-if="node.level <= 2"

增加复选框  show-checkbox

结点唯一id：node-key="catId"

> 再次强调不要直接复制，我写了注释

```vue
<el-tree
         :data="menus"
         show-checkbox  //显示复选框
         :props="defaultProps"  
         :expand-on-click-node="false" //设置节点点击时不展开
         node-key="catId"   
         >
    <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
            <el-button v-if="node.level <= 2"   非叶结点
                       type="text" 
                       size="mini" 
                       @click="() => append(data)">  新增按钮
                Append
            </el-button>
            <el-button  叶子结点
                       v-if="node.childNodes.length == 0"
                       type="text"
                       size="mini"
                       @click="() => remove(node, data)">   删除按钮
                Delete
            </el-button>
        </span>
    </span>
</el-tree>
```

#### 删除分类controller

我们可以删除某个分类，要点如下：

- 如果删除的不是最低级菜单，会提示删除包括父分类和所有子分类
- 删除的时候数据库里还有，只是标记某个字段 标记为不可见了

```java
    @RequestMapping("/delete") // CategoryController
    public R delete(@RequestBody Long[] catIds){
		categoryService.removeByIds(Arrays.asList(catIds));
        return R.ok();
    }
```

测试删除数据，打开postman输入“ http://localhost:88/api/product/category/delete ”，请求方式设置为POST，为了比对效果，可以在删除之前，查询数据库的pms_category表：

![image-20200426112814069](https://i0.hdslb.com/bfs/album/45f17f68467f73ad377dc1ef073ea917ce4895dd.png)

由于delete请求接收的是一个数组，所以这里使用JSON方式，传入了一个数组：

![image-20200426113003531](https://i0.hdslb.com/bfs/album/5ba6fb62200b5f9bfdd2e844be84666af8c5fceb.png)

点击删除后再次查询数据库能够看到cat_id为1000的数据已经被删除了。



但是我们需要修改检查当前菜单是否被引用

修改CategoryController类，添加如下代码：

```java
@RequestMapping("/delete")
public R delete(@RequestBody Long[] catIds){
    //删除之前需要判断待删除的菜单那是否被别的地方所引用。
    //		categoryService.removeByIds(Arrays.asList(catIds));

    categoryService.removeMenuByIds(Arrays.asList(catIds));
    return R.ok();
}
```

```java
@Override // CategoryServiceImpl
public void removeMenuByIds(List<Long> asList) {
    //TODO 1 检查当前的菜单是否被别的地方所引用
    // 2
    baseMapper.deleteBatchIds(asList);
}
```

#### 逻辑删除

然而多数时候，我们并不希望删除数据，而是标记它被删除了，这就是逻辑删除；

> 逻辑删除是mybatis-plus 的内容，会在项目中配置一些内容，告诉此项目执行delete语句时并不删除，只是标志位
>
> 

假设数据库中有字段show_status为0，标记它已经被删除。

![image-20200426115332899](https://i0.hdslb.com/bfs/album/3d7236bdacf088f6ac41499a139233f74b1557db.png)

mybatis-plus的逻辑删除：https://baomidou.com/guide/logic-delete.html#使用方法

> 说明:
>
> 只对自动注入的sql起效:
>
> - 插入: 不作限制
> - 查找: 追加where条件过滤掉已删除数据,且使用 wrapper.entity 生成的where条件会忽略该字段
> - 更新: 追加where条件防止更新到已删除数据,且使用 wrapper.entity 生成的where条件会忽略该字段
> - 删除: 转变为 更新
>
> 例如:
>
> - 删除: `update user set deleted=1 where id = 1 and deleted=0`
> - 查找: `select id,name,deleted from user where deleted=0`
>
> 字段类型支持说明:
>
> - 支持所有数据类型(推荐使用 `Integer`,`Boolean`,`LocalDateTime`)
> - 如果数据库字段使用`datetime`,逻辑未删除值和已删除值支持配置为字符串`null`,另一个值支持配置为函数来获取值如`now()`
>
> 附录:
>
> - 逻辑删除是为了方便数据恢复和保护数据本身价值等等的一种方案，但实际就是删除。
> - 如果你需要频繁查出来看就不应使用逻辑删除，而是以一个状态去表示

配置全局的逻辑删除规则，在“src/main/resources/application.yml”文件中添加如下内容：

```yaml
mybatis-plus:
  mapper-locations: classpath:/mapper/**/*.xml
  global-config:
    db-config:
      id-type: auto
      logic-delete-value: 1
      logic-not-delete-value: 0
```

修改product.entity.CategoryEntity实体类，添加上@TableLogic，表明使用逻辑删除：

```java
	/**
	 * 是否显示[0-不显示，1显示]
	 */
	@TableLogic(value = "1",delval = "0")
	private Integer showStatus;
```

然后在POSTMan中测试一下是否能够满足需要。

#### 日志

另外在“src/main/resources/application.yml”文件中，设置日志级别，打印出SQL语句：

```yaml
logging:
  level:
    com.atguigu.gulimall.product: debug
```

打印的日志：

```verilog
 ==>  Preparing: UPDATE pms_category SET show_status=0 WHERE cat_id IN ( ? ) AND show_status=1 
 ==> Parameters: 1431(Long)
 <==    Updates: 1
 get changedGroupKeys:[]
```

#### 删除效果P51

```html
<!-- slot -->
<span class="custom-tree-node" slot-scope="{ node, data }">
    <span>{{ node.label }}</span>
    <span>
        <el-button v-if="node.level <=2" type="text" size="mini" @click="() => append(data)">添加</el-button>
        <el-button type="text" size="mini" @click="edit(data)">编辑</el-button>
        <el-button
                   v-if="node.childNodes.length==0"
                   type="text"
                   size="mini"
                   @click="() => remove(node, data)"
                   >删除</el-button>
    </span>
</span>
```



#### 前端的拦截逻辑

- 发送的请求：delete

- 发送的数据：`this.$http.adornData(ids, false)`

  - util/httpRequest.js中，封装了一些拦截器
- http.adornParams是封装`get`请求的数据
  
  - ajax的get请求会被缓存，就不会请求服务器了。所以我们在url后面拼接个date（使之无法url不一致），让他每次都请求服务器
- http.adornData是封装`post`请求的数据
  
- ```java
   // 定义http对象，后面定义他的请求拦截器
   const http = axios.create({
     timeout: 1000 * 30,
     withCredentials: true,
     headers: {
       'Content-Type': 'application/json; charset=utf-8'
     }
   })
   
   /**
    * 请求地址处理
    * @param {*} actionName action方法名称
    */
   http.adornUrl = (actionName) => {
     // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
     return (process.env.NODE_ENV !== 'production' 
             && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
   }
   
   /**
    * 请求地址处理
    * @param {*} actionName action方法名称
    */
   http.adornUrl = (actionName) => {
     // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
     return (process.env.NODE_ENV !== 'production' 
             && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
   }
   
   /**
    * get请求参数处理
    * @param {*} params 参数对象
    * @param {*} openDefultParams 是否开启默认参数?
    */
   http.adornParams = (params = {}, openDefultParams = true) => {
     var defaults = {
       't': new Date().getTime()
     }
     return openDefultParams ? merge(defaults, params) : params
   }
   
   /**
    * post请求数据处理
    * @param {*} data 数据对象
    * @param {*} openDefultdata 是否开启默认数据?
    * @param {*} contentType 数据格式
    *  json: 'application/json; charset=utf-8'
    *  form: 'application/x-www-form-urlencoded; charset=utf-8'
    */
   http.adornData = (data = {}, openDefultdata = true, contentType = 'json') => {
     var defaults = {
       't': new Date().getTime()
     }
     data = openDefultdata ? merge(defaults, data) : data
     return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
   }
   
   ```



抽取代码片段vue.code-snippets

```json
{
    "http-get请求":{
        "prefix":"httpget",
        "body":[
            "this.\\$http({",
            "url:this,\\$http.adornUrl(''),",
            "method:'get',",
            "params:this.\\$http.adornParams({})",
            "}).then({data})=>{",
            "})"
        ],
        "description":"httpGET请求"
    },

    "http-post请求":{
        "prefix":"httppost",
        "body":[
            "this.\\$http({",
            "url:this,\\$http.adornUrl(''),",
            "method:'post',",
            "data: this.\\$http.adornData(data, false)",
            "}).then({data})=>{ })"
        ],
        "description":"httpPOST请求"
    }
}
```

- 删除时弹窗确认

- 删除成功弹窗

- 删除后重新展开父节点：重新ajax请求数据，指定展开的基准是:default-expanded-keys="expandedKey"，返回数据后刷新this.expandedKey = [node.parent.data.catId];

  

```js
remove(node, data) {
      var ids = [data.catId];
    // 弹窗 确认
      this.$confirm(`是否删除【${data.name}】菜单?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => { // 点击确定
          this.$http({
              // 给delete发送
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(ids, false)
          }).then(({ data }) => {
              // 删除成功$message
            this.$message({
              message: "菜单删除成功",
              type: "success"
            });
            //刷新出新的菜单
            this.getMenus();
            //设置需要默认展开的菜单
            this.expandedKey = [node.parent.data.catId];
          });
        })
        .catch(// 取消
          () => {});
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.getMenus();
  },
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {}, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {} //如果页面有keep-alive缓存功能，这个函数会触发
};
```

#### dialog对话框

P52：新增

https://element.eleme.cn/#/zh-CN/component/dialog

- 一个button的单击事件函数为@click="dialogVisible = true"
- 一个会话的属性为:visible.sync="dialogVisible"
- 导出的data中"dialogVisible = false"
- 点击确认或者取消后的逻辑都是@click="dialogVisible = false"  关闭会话而已

```js
<el-button type="text" @click="dialogVisible = true">点击打开 Dialog</el-button>

<el-dialog
  title="提示"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
  <span>这是一段信息</span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
</el-dialog>

<script>
  export default {
    data() {
      return {
        dialogVisible: false
      };
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      }
    }
  };
</script>
before-close 仅当用户通过点击关闭图标或遮罩关闭 Dialog 时起效。如果你在 footer 具名 slot 里添加了用于关闭 Dialog 的按钮，那么可以在按钮的点击回调函数里加入 before-close 的相关逻辑。
```

要结合表单

修改 P53

- 点击修改弹出对话框，显示现有内容
- 输入新内容后确定，回显新内容
- 对话框是复用的添加的对话框，点击确定的时候回调的是同一个函数，为了区分当前对话框是单击修改还是点击添加打开的，所以添加一个`dialogType`、`title`属性。然后回调函数进行`if`判断
- 回显时候要发送请求获取最新数据




## 16. 菜单拖动

>  这一节的重要作用是了解一下tree组件

element-ui：https://element.eleme.cn/#/zh-CN/component/tree

- allow-drop拖拽时判定目标节点能否被放置
- 被拖动的当前节点以及所在的父节点总层数不能大于3

|                                  |      |
| -------------------------------- | ---- |
| 同一个菜单内拖动                 | 正常 |
| 拖动到父菜单的前面或后面         | 正常 |
| 拖动到父菜单同级的另外一个菜单中 | 正常 |



关注的焦点在于，拖动到目标节点中，使得目标节点的catlevel+deep小于3即可。

###    拖拽条件与修改顺序/级别

1）拖拽与数据库关联的内容：

- catLevel
- parentCid
- sort

2）拖拽相关函数：

```html
<el-tree
         :data="menus"  绑定的变量
         :props="defaultProps" 配置选项
         :expand-on-click-node="false"  只有点击箭头才会展开收缩
         show-checkbox 显示多选框
         node-key="catId" 数据库的id作为node id
         :default-expanded-keys="expandedKey" 默认展开的数组
         :draggable="draggable" 开启拖拽功能
         :allow-drop="allowDrop"  是否允许拖拽到目标结点，函数为Function(draggingNode源结点, dropNode目标结点, type前中后类型)
         @node-drop="handleDrop"  拖拽成功处理函数，函数为Function(draggingNode源结点, dropNode拖拽成功后的父结点, type前中后类型)
         ref="menuTree"
         >
```

3）函数参数：

- `draggingNode`：正在拖拽的结点
- `dropNode`：拓展成功后的父节点，我们把他称为**目的父节点**
- `type`：分为before、after、inner。拖拽到某个结点上还是两个结点之间

4）先了解一下如何获取结点的深度

```js
在拖拽的时候首先会自动调用allowDrop()函数，他在第一句就调用了this.countNodeLevel(draggingNode);
-----------------------------------------;
// countNodeLevel()函数的作用是遍历拖拽结点的【子节点】，找到其中的最大层级
countNodeLevel(node) {
    //找到所有子节点，求出最大深度
    if (node.childNodes != null && node.childNodes.length > 0) {
        for (let i = 0; i < node.childNodes.length; i++) {
            if (node.childNodes[i].level > this.maxLevel) {
                // 是赋值给了共享变量maxLevel
                this.maxLevel = node.childNodes[i].level;
            }
            // 递归子节点
            this.countNodeLevel(node.childNodes[i]);
        }
    }
}
----------------------------------------;
找到了拖拽结点的最大层级(深度)，那么就可以计算拖拽结点作为根节点的子树深度deep。;
另外注意maxLevel每次拖拽都会更新，是拖拽结点的最大层级;
let deep = Math.abs(this.maxLevel - draggingNode.level) + 1; 
// draggingNode为正在拖拽的结点
```

5）拖拽合法性

我们得到了子树的深度deep，就可以判断这个拖拽合不合法：

拖拽类型：以拖拽后新的父结点为基准分为：

- 结点前、后（两个结点之间）：

  ```js
  deep + dropNode.parent.level <= 3;
  ```

- 中（结点上）：

  ```js
  deep + dropNode.level <= 3;
  ```

6）拖拽合法后的操作

- 先得到拖拽成功后的父节点id、父节点新的子结点（包含了拖拽结点）
- 准备一个update[]数组，有变化的保存到里面，最后提交到数据库。（会变化的有新兄弟结点和拖拽子节点）
- 遍历子节点for i
  - 非draggingNode结点直接push(兄弟结点id，排序)：this.updateNodes.push({ catId: siblings[i].data.catId, sort: i });
  - 是draggingNode结点更新其父节点和sort
    - 还需要更新子节点的level，因为element-ui已经提供了level，我们只需将新的level保存到update中，最后也发送到数据库中即可。
- 保存提交到数据库，弹出成功窗口。
- 刷新菜单，展开对于层级。只需要赋值给expandedKey即可
- 为了防止下次拖拽还有上回的updateNodes信息，所以操作完应该恢复原始状态

##### 7）更改分类controller

对于后端更新数据库，加入controller。用postman测试，

```java
/**
     * 批量修改层级
     {["catId":1,"sort":0],["catId":2,"catLevel":2]}
*/
@RequestMapping("/update/sort")
public R updateSort(@RequestBody CategoryEntity[] category){
    categoryService.updateBatchById(Arrays.asList(category));
    return R.ok();
}
```

8）拖拽开关

P57

为了防止误操作，我们通过edit把拖拽功能开启后才能进行操作。所以添加switch标签，操作是否可以拖拽。我们也可以体会到el-switch这个标签是一个开关

```html
<template>
  <div>
    <el-switch v-model="draggable" active-text="开启拖拽" inactive-text="关闭拖拽"></el-switch>
    <el-button v-if="draggable" @click="batchSave">批量保存</el-button>
    <el-button type="danger" @click="batchDelete">批量删除</el-button>
    <!-- 把menus给data -->
    <el-tree
             :draggable="draggable"
```

##### 9）批量保存

但是现在存在的一个问题是每次拖拽的时候，都会发送请求，更新数据库这样频繁的与数据库交互

现在想要实现一个拖拽过程中不更新数据库，拖拽完成后，统一提交拖拽后的数据。

`<el-button v-if="draggable" @click="batchSave">批量保存</el-button>`

- v-if是指开启开关后才显示
- 开启拖拽后应该使用的是node信息，而不是数据库信息，因为还没同步到数据库。把相关的信息都修改
- 之前为了防止上次数据遗落，归零了展开列表，这样列表又不展开了

现在还存在一个问题，如果是将一个菜单连续的拖拽，最终还放到了原来的位置，但是updateNode中却出现了很多节点更新信息，这样显然也是一个问题。

##### 10）批量删除与调用内置函数

`<el-button type="danger" @click="batchDelete">批量删除</el-button>`

红框

getCheckedNodes()返回当前选中的所有结点

如何调用内

```html
<el-tree
         。。。
         ref="menuTree"
         />
然后在js里
this.$refs.menuTree.getCheckedNodes();
他有两个参数，默认的是我们想要用的
```

- 确认框
- 确认后发送ajax
- 刷新菜单

## 17. 品牌管理菜单

后台：系统管理/菜单管理/新增

<img src="https://i0.hdslb.com/bfs/album/ac22f869f1b9a832f5e2cae83d69de4a01a0b20e.png" alt="image-20200428164054517" style="zoom: 67%;" />



（2）将逆向工程product得到的resources\src\views\modules\product文件拷贝到gulimall/renren-fast-vue/src/views/modules/product目录下，也就是下面的两个文件

- brand.vue ： 显示的表单
-  brand-add-or-update.vue：添加和更改功能

但是显示的页面没有新增和删除功能，这是因为权限控制的原因，

![image-20200428170325515](https://i0.hdslb.com/bfs/album/da3d816b6131f6c3118f2667a64e8c52f7b2796a.png)

```vue
<el-button v-if="isAuth('product:brand:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
<el-button v-if="isAuth('product:brand:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
```

查看“isAuth”的定义位置：

![image-20200428170437592](https://i0.hdslb.com/bfs/album/19e189b9d05bfca87acef84c841e7a54e91deb55.png)

它是在“index.js”中定义，暂时将它设置为返回值为true，即可显示添加和删除功能。

再次刷新页面能够看到，按钮已经出现了：

![image-20200428170644511](https://i0.hdslb.com/bfs/album/ee8d0d12f370c97617152f03ddbaba84bc766a54.png)



进行添加 测试成功， 进行修改 也会自动回显

> build/webpack.base.conf.js 中注释掉createLintingRule()函数体，不进行lint语法检查

### “显示状态”按钮

brand.vue

```xml
<template slot-scope="scope"> scope属性包含了一整行数据
  定义显示效果
  <el-switch
    v-model="scope.row.showStatus"
    active-color="#13ce66"
    inactive-color="#ff4949"
    @change="updateBrandStatus(scope.row)" 变化会调用函数
    :active-value = "1"
    :inactive-value	= "0"
  ></el-switch>
</template>

另外导入了
<script>
import AddOrUpdate from "./brand-add-or-update";
他作为弹窗被brand.vue使用
<!-- 弹窗, 新增 / 修改 -->
<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    
AddOrUpdate具体是个会话窗
<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
```

brand-add-or-update.vue

```xml
<el-form-item label="显示状态" prop="showStatus">
    <el-switch v-model="dataForm.showStatus"
               active-color="#13ce66"
               inactive-color="#ff4949"
               :active-value="1"
               :inactive-value="0"
               >
    </el-switch>
</el-form-item>
```

```javascript
//更新开关的状态
    updateBrandStatus(data) { // 传入了改变行的数据
      console.log("最新状态", data);
      let {brandId,showStatus} = data;
      this.$http({
        url: this.$http.adornUrl("/product/brand/update"),
        method: "post",
        data: this.$http.adornData({brandId,showStatus}, false)
      }).then(({ data }) => {

        this.$message({
          message: "状态更新成功",
          type: "success"
        });

      });
    },
```

更新品牌对应的controller

```java
@RestController
@RequestMapping("product/brand")
public class BrandController {
    /** * 修改 */
    @RequestMapping("/update")
    public R update(@RequestBody BrandEntity brand){
        brandService.updateById(brand);

        return R.ok();
    }

```

品牌实体

```java
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 品牌id */
	@TableId
	private Long brandId;
	/*** 品牌名 */
	private String name;
	/*** 品牌logo地址 */
	private String logo;
	/*** 介绍 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	private Integer showStatus;
	/** * 检索首字母 */
	private String firstLetter;
	/** * 排序 */
	private Integer sort;
}
```







### 阿里云上传

> 用过图床的同学这部分会很熟悉，如我的图片就是放到阿里云图床上的https://fermhan.oss-cn-qingdao.aliyuncs.com/guli/image-20200428182755992.png

和传统的单体应用不同，这里我们选择将数据上传到分布式文件服务器上。

这里我们选择将图片放置到阿里云上，使用对象存储。

阿里云上使使用对象存储方式：

![image-20200428182755992](https://i0.hdslb.com/bfs/album/695f7d5010aadb6c22b096b12692dec4d3f01f9d.png)

##### 创建Bucket

创建Bucket（作为项目）

<img src="https://i0.hdslb.com/bfs/album/61ca05d4583aae728faac0640a45f42828ca3d55.png" style="zoom:67%;" />

 

上传文件：上传成功后，取得图片的URL

<img src="https://i0.hdslb.com/bfs/album/d35eef003a4be34219e2585f25587e37fca31bcc.png" style="zoom:67%;" />



这种方式是手动上传图片，实际上我们可以在程序中设置自动上传图片到阿里云对象存储。

上传模型：

<img src="https://i0.hdslb.com/bfs/album/a54ca3c11303d52005ca7dd6b11231f62be7f043.png" alt="image-20200428184029655" style="zoom:50%;" />

- 上传的账号信息存储在应用服务器
- 上传先找应用服务器要一个**policy上传策略**，生成**防伪签名**



### 使用代码上传

查看阿里云关于文件上传的帮助： https://help.aliyun.com/document_detail/32009.html?spm=a2c4g.11186623.6.768.549d59aaWuZMGJ 

##### 1.1）添加依赖包

在Maven项目中加入依赖项（推荐方式）

在 Maven 工程中使用 OSS Java SDK，只需在 pom.xml 中加入相应依赖即可。以 3.8.0 版本为例，在 pom内加入如下内容：

```xml
<dependency>
    <groupId>com.aliyun.oss</groupId>
    <artifactId>aliyun-sdk-oss</artifactId>
    <version>3.8.0</version>
</dependency>
```

##### 1.2）上传文件流

以下代码用于上传文件流：

```java
// Endpoint以杭州为例，其它Region请按实际情况填写。
String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
String accessKeyId = "<yourAccessKeyId>";
String accessKeySecret = "<yourAccessKeySecret>";

// 创建OSSClient实例。
OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
InputStream inputStream = new FileInputStream("<yourlocalFile>");
ossClient.putObject("<yourBucketName>", "<yourObjectName>", inputStream);

// 关闭OSSClient。
ossClient.shutdown();
```

上面代码的信息可以通过如下查找：

- endpoint的取值：点击概览就可以看到你的endpoint信息，endpoint在这里就是上海等地区，如 oss-cn-qingdao.aliyuncs.com
- bucket域名：就是签名加上bucket，如gulimall-fermhan.oss-cn-qingdao.aliyuncs.com

##### accessKey的获取

accessKeyId和accessKeySecret需要创建一个RAM账号：

![image-20200428190532924](https://i0.hdslb.com/bfs/album/80898e4df3f15156d4d12bdb52ee2192b81a5b90.png)

- 选上`编程访问`

创建用户完毕后，会得到一个“AccessKey ID”和“AccessKeySecret”，然后复制这两个值到代码的“AccessKey ID”和“AccessKeySecret”。

另外还需要添加访问控制权限：

![image-20200428191518591](https://i0.hdslb.com/bfs/album/b47c53219dd67dc9be3a81f78ab3c42abed5da90.png)





```java
@Test
public void testUpload() throws FileNotFoundException {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "oss-cn-shanghai.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    String accessKeyId = "LTAI4G4W1RA4JXz2QhoDwHhi";
    String accessKeySecret = "R99lmDOJumF2x43ZBKT259Qpe70Oxw";

    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    // 上传文件流。
    InputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Pictures\\timg.jpg");
    // 上传
    ossClient.putObject("gulimall-images", "time.jpg", inputStream);

    // 关闭OSSClient。
    ossClient.shutdown();
    System.out.println("上传成功.");
}
```

2）更为简单的使用方式，是使用SpringCloud Alibaba来管理oss

![image-20200428195507730](https://i0.hdslb.com/bfs/album/df88ce332e5a608373dab9baa243cd3b5c43ae96.png)

详细使用方法，见： https://help.aliyun.com/knowledge_detail/108650.html  

（1）添加依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alicloud-oss</artifactId>
    <version>2.2.0.RELEASE</version>
</dependency>
```

（2）创建“AccessKey ID”和“AccessKeySecret”

（3）配置key，secret和endpoint相关信息

```yaml
      access-key: LTAI4G4W1RA4JXz2QhoDwHhi
      secret-key: R99lmDOJumF2x43ZBKT259Qpe70Oxw
      oss:
        endpoint: oss-cn-shanghai.aliyuncs.com
```

（4）注入OSSClient并进行文件上传下载等操作

![image-20200428224840535](https://i0.hdslb.com/bfs/album/22c5831f9567c56f71cfc5b1c75bbcb4d04dc72b.png)



问题：但是这样来做还是比较麻烦，如果以后的上传任务都交给gulimall-product来完成（中转），显然耦合度高。最好单独新建一个Module来完成文件上传任务。

### gulimall-third-party微服务

添加依赖，将原来gulimall-common中的“spring-cloud-starter-alicloud-oss”依赖移动到该项目中

```xml
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alicloud-oss</artifactId>
            <version>2.2.0.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>com.atguigu.gulimall</groupId>
            <artifactId>gulimall-common</artifactId>
            <version>1.0-SNAPSHOT</version>
            <exclusions>
                <exclusion>
                    <groupId>com.baomidou</groupId>
                    <artifactId>mybatis-plus-boot-starter</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

 另外也需要在“pom.xml”文件中，添加如下的依赖管理

```xml
<dependencyManagement>

        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.2.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

主启动类@EnableDiscoveryClient开启服务的注册和发现

```java
@EnableDiscoveryClient 
```

在nacos中注册

（1）在nacos创建命名空间“ gulimall-third-party ”

![image-20200429075831984](https://i0.hdslb.com/bfs/album/755ebcbdcbc0144dc26b800d5ed1c85e3f9ff8bb.png)

（2）在“ gulimall-third-party”命名空间中，创建“ gulimall-third-party.yml”文件

```yaml
spring:
  cloud:
    alicloud:
      access-key: LTAI4G4W1RA4JXz2QhoDwHhi
      secret-key: R99lmDOJumF2x43ZBKT259Qpe70Oxw
      oss:
        endpoint: oss-cn-shanghai.aliyuncs.com
```

编写配置文件application.yml

```yaml
server:
  port: 30000

spring:
  application: 
    name: gulimall-third-party
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848

logging:
  level:
    com.atguigu.gulimall.product: debug
```

> 注意去网关里配置转发，/api/thirdparty/...的路径改完后只有/...，但是其他服务是不去服务名的

bootstrap.properties

```properties
spring.cloud.nacos.config.name=gulimall-third-party
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
spring.cloud.nacos.config.namespace=9054e55c-b667-428c-b71d-0f2b42a6acff
spring.cloud.nacos.config.extension-configs[0].data-id=oss.yml
spring.cloud.nacos.config.extension-configs[0].group=DEFAULT_GROUP
spring.cloud.nacos.config.extension-configs[0].refresh=true
```

nacos端新建oss.yml

```yaml
spring:
    cloud:
        alicloud:
            access-key: LTAI4G3ewgWMxsrnaaeDuT1B
            secret-key: Kdk4YLfj0prQOfPHzzQf9sIbdD0YeV
            oss: 
                endpoint: oss-cn-qingdao.aliyuncs.com
```

编写测试类

```java
package com.atguigu.gulimall.thirdparty;

@SpringBootTest
class GulimallThirdPartyApplicationTests {
    @Autowired
    OSSClient ossClient;

    @Test
    public void testUpload() throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-qingdao.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4G4W1RA4JXz2QhoDwHhi";
        String accessKeySecret = "R99lmDOJumF2x43ZBKT259Qpe70Oxw";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

         //上传文件流。
        InputStream inputStream = new FileInputStream("C:\\Users\\HAN\\Downloads\\123.jpg");
        ossClient.putObject("gulimall-fermhan", "333.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        System.out.println("上传成功.");
    }
}
```

```java
    @Test
    public void testUpload() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("C:\\Users\\HAN\\Downloads\\123.jpg");
        // 参数1位bucket  参数2位最终名字
        ossClient.putObject("gulimall-fermhan","321.jpg",inputStream);
        ossClient.shutdown();
    }
```

上面的逻辑是先把字节流给服务器，服务器转发给阿里云。那么能不能直接从前端发往阿里云呢？就省去了我们服务器转发的消耗

##### 改进：服务端签名后直传

教程： https://help.aliyun.com/document_detail/31926.html?spm=a2c4g.11186623.6.1527.228d74b8V6IZuT 

**背景**

文件还需要传到java后端中转一下，没有必要，如何能直接传到oss就好了。所以考虑把AccessKeyID和AcessKeySecret给前端传过去，前端直接传到oss？

但是，采用JavaScript客户端直接签名（参见[JavaScript客户端签名直传](https://help.aliyun.com/document_detail/31925.html#concept-frd-4gy-5db)）时，AccessKeyID和AcessKeySecret会暴露在前端页面，因此存在严重的安全隐患。因此，ali-OSS提供了**服务端签名**后直传的方案。

**原理介绍**

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/6875011751/p1472.png)

服务端签名后直传的原理如下：

1. 用户发送上传**Policy请求**到应用服务器。
2. 应用服务器返回上传**Policy**和签名给用户。
3. 用户直接上传数据到OSS。

<img src="http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/zh-CN/3156348951/p139016.png" alt="时序图" style="zoom:50%;" />

java用法：https://help.aliyun.com/document_detail/91868.html?spm=a2c4g.11186623.2.10.97e17d9cfwODvA

编写“com.atguigu.gulimall.thirdparty.controller.`OssController`”类：该类用于获取签名，就是说这个签名在一段时间内有效，你先拿去用会

```java
@RestController
public class OssController {

    @Autowired
    OSS ossClient;
    @Value ("${spring.cloud.alicloud.oss.endpoint}")
    String endpoint ;

    @Value("${spring.cloud.alicloud.oss.bucket}")
    String bucket ;

    @Value("${spring.cloud.alicloud.access-key}")
    String accessId ;
    @Value("${spring.cloud.alicloud.secret-key}")
    String accessKey ;
    
    @RequestMapping("/oss/policy")
    public Map<String, String> policy(){

        String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint

        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = format; // 用户上传文件时指定的前缀。

        Map<String, String> respMap=null;
        try {
            // 签名有效事件
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            // 签名
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap= new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return respMap;
    }
}
```

上面的意思是说用户通过url请求得到一个policy，要拿这个东西直接传到阿里云，不要去服务器了

测试： http://localhost:30000/oss/policy   返回签名

```json
{
    "accessid":"LTAI4G3ewgWMxsrnaaeDuT1B",
    "policy":"eyJleHBpcmF0aW9uIjoiMjAyMS0wMi0xNFQxMDoyOToxMS43ODhaIiwiY29uZGl0aW9ucyI6W1siY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsMTA0ODU3NjAwMF0sWyJzdGFydHMtd2l0aCIsIiRrZXkiLCIyMDIxLTAyLTE0Il1dfQ==",
    "signature":"0OXDXrQ1vRNl61N5IaZXRFckCKM=",
    "dir":"2021-02-14",
    "host":"https://gulimall-fermhan.oss-cn-qingdao.aliyuncs.com",
    "expire":"1613298551"}
```

在该微服务中测试通过，但是我们不能对外暴露端口或者说为了统一管理，我们还是让用户请求网关然后转发过来

以后在上传文件时的访问路径为“ http://localhost:88/api/thirdparty/oss/policy”，通过网关转发

在“gulimall-gateway”中配置路由规则：

```yaml
        - id: third_party_route
          uri: lb://thirdparty
          predicates:
            - Path=/api/thirdparty/**
          filters: # 去掉/api/   # 之前这里写错了
            - RewritePath=/api/(?<segment>/?.*),/$\{segment}
```

测试是否能够正常跳转： http://localhost:88/api/thirdparty/oss/policy 

### 上传组件

放置项目提供的upload文件夹到components/目录下，一个是单文件上传，另外一个是多文件上传

- policy.js封装一个Promise，发送/thirdparty/oss/policy请求。vue项目会自动加上api前缀
- multiUpload.vue多文件上传。要改，改方式如下
- singleUpload.vue单文件上传。要替换里面的action中的内容。action="http://gulimall-fermhan.oss-cn-qingdao.aliyuncs.com"

```shell
gulimall\renren-fast-vue\src\components\upload
```

要修改vue项目中心品牌logo地址，要改成下面形式：

![](https://i0.hdslb.com/bfs/album/aa0abc5767772b8dfe022c3c73e5542422163a96.png)



brand-add-or-update.vue中

- 修改el-form-item label="品牌logo地址"内容。
- 要使用文件上传组件，先导入import SingleUpload from "@/components/upload/singleUpload";
- 填入`<single-upload v-model="dataForm.logo"></single-upload>`
- 写明要使用的组件components: { SingleUpload },

点击一下文件上传，发现发送了两个请求

`localhost:88/api/thirdparty/oss/policy?t=1613300654238`

![](https://i0.hdslb.com/bfs/album/12a53292287294922372057315b48735a59c1664.png)

> 注： [特立独行ベ猫](https://space.bilibili.com/59372233) 的vue前端，他policy.js中多写了一个/，导致404，去掉就好了
>
> 正确形式：localhost:88/api/thirdparty/oss/policy?t=1613300654238

我们在后端准备好了签名controller，那么前端是在哪里获取的呢

policy.js

逻辑为先去访问我们的服务器获取policy，然后取阿里云，所以我们至少要发送2个请求

```js
import http from '@/utils/httpRequest.js'
export function policy() {
   return  new Promise((resolve,reject)=>{
        http({
            // 先去获取签名
            url: http.adornUrl("/third/party/oss/policy"),
            method: "get",
            params: http.adornParams({})
        }).then(({ data }) => {
            // 然后拿着签名去请求数据
            resolve(data);
        })
    });
}
```

而文件上传前调用的方法： :before-upload="beforeUpload"

```js
发现该方法返回了一个new Promise，调用了policy()，该方法是policy.js中的
import { policy } from "./policy";

....
beforeUpload(file) {
      let _self = this;
      return new Promise((resolve, reject) => {
          
        policy() // 获取签名后得到相应
          .then(response => {
            // 意思是说policy获取到签名后，把签名信息保存起来
            // console.log("这是什么${filename}");
            _self.dataObj.policy = response.data.policy;
            _self.dataObj.signature = response.data.signature;
            _self.dataObj.ossaccessKeyId = response.data.accessid;
            _self.dataObj.key = response.data.dir +getUUID()+"_${filename}";
            _self.dataObj.dir = response.data.dir;
            _self.dataObj.host = response.data.host;
            resolve(true);
            // 总的来说什么意思呢？
            // 上传之前先请求签名，保存起来签名
            // 根据action="http://gulimall-fermhan.oss-cn-qingdao.aliyuncs.com"
            // 结合data信息，提交到云端
          })
          .catch(err => {
            console.log("出错了...",err)
            reject(false);
          });
      });
    },
```

在vue中看是response.data.policy，在控制台看response.policy。所以去java里面改返回值为R。return R.ok().put("data",respMap);



##### 阿里云开启跨域

开始执行上传，但是在上传过程中，出现了跨域请求问题：（从我们的服务去请求oss服务，我们前面说过了，跨域不是浏览器限制了你，而是新的服务器限制的问题，所以得去阿里云设置）

```
报错：
Access to XMLHttpRequest at 'http://gulimall-f.oss-cn-qingdao.aliyuncs.com/' from origin 'http://localhost:8001' has been blocked by CORS policy: Response to preflight request doesn't pass access control check: No 'Access-Control-Allow-Origin' header is present on the requested resource.
意思说本来是在页面localhost上，却要把请求发给aliyuncs
```

这又是一个跨域的问题，解决方法就是在阿里云上开启**跨域访问**：

![image-20200429124940091](https://i0.hdslb.com/bfs/album/95a7ae737bc66ef55d38f34470c35c95d09d82da.png)

再次执行文件上传。

注意上传时他的key变成了response.data.dir +getUUID()+"_${filename}";

优化：上传后显示图片地址

显示图片：

```xml
<el-table-column prop="logo" header-align="center" align="center" label="品牌logo地址">
    <template slot-scope="scope">
        <!-- 自定义表格+自定义图片 -->
        <img :src="scope.row.logo" style="width: 100px; height: 80px" />
    </template>
</el-table-column>
```

修改vue项目的element-ui脚手架的问题，没有导入element-ui的image组件

## 18. JSR303校验

问题引入：填写form时应该有前端校验，后端也应该有校验

- 前端的校验是element-ui表单验证https://element.eleme.cn/#/zh-CN/component/form

  - Form 组件提供了表单验证的功能，只需要通过 `rules` 属性传入约定的验证规则，并将 Form-Item 的 `prop` 属性设置为需校验的字段名即可。校验规则参见 [async-validator](https://github.com/yiminghe/async-validator)

  - 使用自定义校验规则可以解决字母限制的问题

    ```js
    var validatePass2 = (rule, value, callback) => {
        if (value === '') {
            callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
            callback(new Error('两次输入密码不一致!'));
        } else {
            callback();
        }
    };
    return {
        rules: {
            checkPass: [
                { validator: validatePass2, trigger: 'blur' }
            ],
    ```

    

- 后端：@NotNull等

### @NotNull等

步骤1：使用校验注解

在Java中提供了一系列的校验方式，它这些校验方式在“`javax.validation.constraints`”包中，提供了如@Email，@NotNull等注解。

```xml
<!--jsr3参数校验器-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
里面依赖了hibernate-validator
```



在非空处理方式上提供了@NotNull，@NotBlank和@NotEmpty

（1）@NotNull 该属性不能为null

（2）@NotEmpty 该字段不能为null或`""`

支持以下几种类型

- CharSequence (length of character sequence is evaluated)字符序列（字符序列长度的计算）
- Collection (collection size is evaluated) 集合长度的计算
- Map (map size is evaluated)  map长度的计算
- Array (array length is evaluated)  数组长度的计算
- 上面什么意思呢？就是说如果标注的是map，它会帮你看长度

（3）@NotBlank：不能为空，不能仅为一个空格

### @Valid内置异常

> 这里内置异常的意思是发生异常时返回的json不是我们的R对象，而是mvc的内置类

步骤2：controller中加校验注解@Valid，开启校验，

```java
@RequestMapping("/save")
public R save(@Valid @RequestBody BrandEntity brand){
    brandService.save(brand);

    return R.ok();
}
```

测试： http://localhost:88/api/product/brand/save 

在postman种发送上面的请求，可以看到返回的甚至不是R对象

```json
{
    "timestamp": "2020-04-29T09:20:46.383+0000",
    "status": 400,
    "error": "Bad Request",
    "errors": [
        {
            "codes": [
                "NotBlank.brandEntity.name",
                "NotBlank.name",
                "NotBlank.java.lang.String",
                "NotBlank"
            ],
            "arguments": [
                {
                    "codes": [
                        "brandEntity.name",
                        "name"
                    ],
                    "arguments": null,
                    "defaultMessage": "name",
                    "code": "name"
                }
            ],
            "defaultMessage": "不能为空",
            "objectName": "brandEntity",
            "field": "name",
            "rejectedValue": "",
            "bindingFailure": false,
            "code": "NotBlank"
        }
    ],
    "message": "Validation failed for object='brandEntity'. Error count: 1",
    "path": "/product/brand/save"
}
```

能够看到"defaultMessage": "不能为空"，这些错误消息定义在“`hibernate-validator`”的“\org\hibernate\validator\ValidationMessages_zh_CN.properties”文件中。在该文件中定义了很多的错误规则：

```properties
javax.validation.constraints.AssertFalse.message     = 只能为false
javax.validation.constraints.AssertTrue.message      = 只能为true
javax.validation.constraints.DecimalMax.message      = 必须小于或等于{value}
javax.validation.constraints.DecimalMin.message      = 必须大于或等于{value}
javax.validation.constraints.Digits.message          = 数字的值超出了允许范围(只允许在{integer}位整数和{fraction}位小数范围内)
javax.validation.constraints.Email.message           = 不是一个合法的电子邮件地址
javax.validation.constraints.Future.message          = 需要是一个将来的时间
javax.validation.constraints.FutureOrPresent.message = 需要是一个将来或现在的时间
javax.validation.constraints.Max.message             = 最大不能超过{value}
javax.validation.constraints.Min.message             = 最小不能小于{value}
javax.validation.constraints.Negative.message        = 必须是负数
javax.validation.constraints.NegativeOrZero.message  = 必须是负数或零
javax.validation.constraints.NotBlank.message        = 不能为空
javax.validation.constraints.NotEmpty.message        = 不能为空
javax.validation.constraints.NotNull.message         = 不能为null
javax.validation.constraints.Null.message            = 必须为null
javax.validation.constraints.Past.message            = 需要是一个过去的时间
javax.validation.constraints.PastOrPresent.message   = 需要是一个过去或现在的时间
javax.validation.constraints.Pattern.message         = 需要匹配正则表达式"{regexp}"
javax.validation.constraints.Positive.message        = 必须是正数
javax.validation.constraints.PositiveOrZero.message  = 必须是正数或零
javax.validation.constraints.Size.message            = 个数必须在{min}和{max}之间

org.hibernate.validator.constraints.CreditCardNumber.message        = 不合法的信用卡号码
org.hibernate.validator.constraints.Currency.message                = 不合法的货币 (必须是{value}其中之一)
org.hibernate.validator.constraints.EAN.message                     = 不合法的{type}条形码
org.hibernate.validator.constraints.Email.message                   = 不是一个合法的电子邮件地址
org.hibernate.validator.constraints.Length.message                  = 长度需要在{min}和{max}之间
org.hibernate.validator.constraints.CodePointLength.message         = 长度需要在{min}和{max}之间
org.hibernate.validator.constraints.LuhnCheck.message               = ${validatedValue}的校验码不合法, Luhn模10校验和不匹配
org.hibernate.validator.constraints.Mod10Check.message              = ${validatedValue}的校验码不合法, 模10校验和不匹配
org.hibernate.validator.constraints.Mod11Check.message              = ${validatedValue}的校验码不合法, 模11校验和不匹配
org.hibernate.validator.constraints.ModCheck.message                = ${validatedValue}的校验码不合法, ${modType}校验和不匹配
org.hibernate.validator.constraints.NotBlank.message                = 不能为空
org.hibernate.validator.constraints.NotEmpty.message                = 不能为空
org.hibernate.validator.constraints.ParametersScriptAssert.message  = 执行脚本表达式"{script}"没有返回期望结果
org.hibernate.validator.constraints.Range.message                   = 需要在{min}和{max}之间
org.hibernate.validator.constraints.SafeHtml.message                = 可能有不安全的HTML内容
org.hibernate.validator.constraints.ScriptAssert.message            = 执行脚本表达式"{script}"没有返回期望结果
org.hibernate.validator.constraints.URL.message                     = 需要是一个合法的URL

org.hibernate.validator.constraints.time.DurationMax.message        = 必须小于${inclusive == true ? '或等于' : ''}${days == 0 ? '' : days += '天'}${hours == 0 ? '' : hours += '小时'}${minutes == 0 ? '' : minutes += '分钟'}${seconds == 0 ? '' : seconds += '秒'}${millis == 0 ? '' : millis += '毫秒'}${nanos == 0 ? '' : nanos += '纳秒'}
org.hibernate.validator.constraints.time.DurationMin.message        = 必须大于${inclusive == true ? '或等于' : ''}${days == 0 ? '' : days += '天'}${hours == 0 ? '' : hours += '小时'}${minutes == 0 ? '' : minutes += '分钟'}${seconds == 0 ? '' : seconds += '秒'}${millis == 0 ? '' : millis += '毫秒'}${nanos == 0 ? '' : nanos += '纳秒'}

```



想要自定义错误消息，可以覆盖默认的错误提示信息，如@NotBlank的默认message是

```java
public @interface NotBlank {

	String message() default "{javax.validation.constraints.NotBlank.message}";
```

可以在添加注解的时候，修改message：

```java
	@NotBlank(message = "品牌名必须非空")
	private String name;
```

当再次发送请求时，得到的错误提示信息：

```json
{
    "timestamp": "2020-04-29T09:36:04.125+0000",
    "status": 400,
    "error": "Bad Request",
    "errors": [
        {
            "codes": [
                "NotBlank.brandEntity.name",
                "NotBlank.name",
                "NotBlank.java.lang.String",
                "NotBlank"
            ],
            "arguments": [
                {
                    "codes": [
                        "brandEntity.name",
                        "name"
                    ],
                    "arguments": null,
                    "defaultMessage": "name",
                    "code": "name"
                }
            ],
            "defaultMessage": "品牌名必须非空",
            "objectName": "brandEntity",
            "field": "name",
            "rejectedValue": "",
            "bindingFailure": false,
            "code": "NotBlank"
        }
    ],
    "message": "Validation failed for object='brandEntity'. Error count: 1",
    "path": "/product/brand/save"
}
```

但是返回的错误不是R对象，影响接收端的接收，我们可以通过局部异常处理或者统一一次处理解决

### 局部异常处理BindResult

步骤3：给校验的Bean后，紧跟一个BindResult，就可以获取到校验的结果。拿到校验的结果，就可以自定义的封装。

如下两个方法是一体的

```java
@RequestMapping("/save")
public R save(@Valid @RequestBody BrandEntity brand){
    brandService.save(brand);

    return R.ok();
}

@RequestMapping("/save")
public R save(@Valid @RequestBody BrandEntity brand,
              BindingResult result){ // 手动处理异常

    if( result.hasErrors()){
        Map<String,String> map=new HashMap<>();
        //1.获取错误的校验结果
        result.getFieldErrors().forEach((item)->{
            //获取发生错误时的message
            String message = item.getDefaultMessage();
            //获取发生错误的字段
            String field = item.getField();
            map.put(field,message);
        });
        return R.error(400,"提交的数据不合法").put("data",map);
    }else {

    }
    brandService.save(brand);

    return R.ok();
}
```

这种是针对于该请求设置了一个内容校验，如果针对于每个请求都单独进行配置，显然不是太合适，实际上可以统一的对于异常进行处理。

### 统一异常处理`@ExceptionHandler`

上文说到 @ ExceptionHandler 需要进行异常处理的方法必须与出错的方法在同一个Controller里面。那么当代码加入了 @ControllerAdvice，则不需要必须在同一个 controller 中了。这也是 Spring 3.2 带来的新特性。从名字上可以看出大体意思是控制器增强。 也就是说，@controlleradvice + @ ExceptionHandler 也可以实现全局的异常捕捉。

（1）抽取一个异常处理类

- `@ControllerAdvice`标注在类上，通过“basePackages”能够说明处理哪些路径下的异常。
- `@ExceptionHandler(value = 异常类型.class) `标注在方法上

```java
@Slf4j
// @RestControllerAdvice和@ControllerAdvice的关系类似于@RestController
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller")//管理的controller
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = Exception.class) // 也可以返回ModelAndView
    public R handleValidException(MethodArgumentNotValidException exception){

        Map<String,String> map=new HashMap<>();
        // 获取数据校验的错误结果
        BindingResult bindingResult = exception.getBindingResult();
        // 处理错误
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String message = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            map.put(field,message);
        });

        log.error("数据校验出现问题{},异常类型{}",exception.getMessage(),exception.getClass());

        return R.error(400,"数据校验出现问题").put("data",map);
    }
}
```

（2）测试： http://localhost:88/api/product/brand/save 

<img src="https://i0.hdslb.com/bfs/album/4e630bec97630fa9d7731dabc27c18c4bc745400.png" alt="image-20200429183334783" style="zoom:50%;" />



#### （3）默认异常处理

```java
@ExceptionHandler(value = Throwable.class)//异常的范围更大
public R handleException(Throwable throwable){
    log.error("未知异常{},异常类型{}",
              throwable.getMessage(),
              throwable.getClass());
    return R.error(BizCodeEnum.UNKNOW_EXEPTION.getCode(),
                   BizCodeEnum.UNKNOW_EXEPTION.getMsg());
}
```

#### （4）错误状态码

上面代码中，针对于错误状态码，是我们进行随意定义的，然而正规开发过程中，错误状态码有着严格的定义规则，如该在项目中我们的错误状态码定义

上面的用法主要是通过`@Controller+@ExceptionHandler`来进行异常拦截处理

BizCodeEnum

为了定义这些错误状态码，我们可以单独定义一个常量类，用来存储这些错误状态码

```java
package com.atguigu.common.exception;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 物流
 */
public enum BizCodeEnum {

    UNKNOW_EXEPTION(10000,"系统未知异常"),

    VALID_EXCEPTION( 10001,"参数格式校验失败");

    private int code;
    private String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
```

（5）测试： http://localhost:88/api/product/brand/save 

<img src="https://i0.hdslb.com/bfs/album/d095a8eba3db30f002119144d52e75d9addcd444.png" alt="image-20200429191830967" style="zoom:67%;" />

可以参考下：https://blog.csdn.net/github_36086968/article/details/103115128

## 19. 分组校验功能（多场景校验）

前面解决了统一异常处理，但是现状有新的需求是对同一实体类参数也要区分场景

如果新增和修改两个接口需要验证的字段不同，比如id字段，新增可以不传递，但是修改必须传递id，我们又不可能写两个vo来满足不同的校验规则。所以就需要用到分组校验来实现。

步骤：

- 创建分组接口Insert.class    Update.class
- 在VO的属性中标注@NotBlank等注解，并指定要使用的分组，如`@NotNull(message = "用户姓名不能为空",groups = {Insert.class,Update.class})`
- controller的方法上或者方法参数上写要处理的分组的接口信息，如`@Validated(AddGroup.class)`

### 1、@NotNull(groups={A.class})

1、给校验注解，标注上groups，指定什么情况下才需要进行校验

如：指定在更新和添加的时候，都需要进行校验。新增时不需要带id，修改时必须带id

在实体类的统一属性上添加多个不同的校验注解

```java
@NotNull(message = "修改必须定制品牌id", groups = {UpdateGroup.class})
@Null(message = "新增不能指定id", groups = {AddGroup.class})
@TableId
private Long brandId;


/**
* 品牌logo地址 修改可以不带上logoURL
*/
@NotBlank(groups = {AddGroup.class})
@URL(message = "logo必须是一个合法的URL地址", groups={AddGroup.class, UpdateGroup.class})
private String logo;
注意上面因为@NotBlank没有指定UpdateGroup分组，所以不生效。此时update时可以不携带，但带了一定得是url地址
```

> 在这种情况下，没有指定分组的校验注解，默认是不起作用的。想要起作用就必须要加groups。

### 2、@Validated

业务方法参数上使用@Validated注解

@Validated的value值指定要使用的一个或多个分组

JSR-303 defines validation groups as custom annotations which an application declares for the sole purpose of using 
them as type-safe group arguments, as implemented in SpringValidatorAdapter.

 JSR-303 将验证组定义为自定义注释，应用程序声明的唯一目的是将它们用作类型安全组参数，如 SpringValidatorAdapter 中实现的那样。 

Other SmartValidator implementations may support class arguments in other ways as well.

 其他SmartValidator 实现也可以以其他方式支持类参数。 

```java
// 新增场景添加 新增分组注解
@RequestMapping("/save")  
public R save(@Validated(AddGroup.class) @RequestBody BrandEntity brand) {
    brandService.save(brand);

    return R.ok();
}

// 删除场景添加 删除分组注解
@RequestMapping("/delete")
public R delete(@RequestBody Long[] brandIds) {
    brandService.removeByIds(Arrays.asList(brandIds));

    return R.ok();
}
```

总结：controller接收到之后，根据@Validated表明的分组信息，品牌对应的校验注解。

### 3、分组校验的默认校验

这里要是指定了分组，实体类上的注解就是指定了分组的注解才生效，

没有指定分组的默认不生效，要是没有指定分组，就是对没有指定分组的注解生效，指定分组的注解就不生效了

但是可以在自定义的异常分组接口中继承`Default`类。所有没有写明group的都属于Default分组。



> 此外还可以在实体类上标注@GroupSequece({A.class,B.class})指定校验顺序
>
> 通过@GroupSequence指定验证顺序：先验证A分组，如果有错误立即返回而不会验证B分组，接着如果A分组验证通过了，那么才去验证B分组，最后指定User.class表示那些没有分组的在最后。这样我们就可以实现按顺序验证分组了。
>
> 关于Default，此处我springvalidation默认生成的验证接口，验证的范围是所有带有验证信息的属性，
>
> 若是属性上方写了验证组，则是验证该组内的属性
>
> 若是验证实体类类上写了GroupSequence({}) 则说明重写了Default验证接口，Default就按照GroupSequence里所写的组信息进行验证

## 20. 自定义校验注解

 Hibernate Validator提供了一系列内置的校验注解，可以满足大部分的校验需求。但是，仍然有一部分校验需要特殊定制，例如某个字段的校验，我们提供两种校验强度，当为`normal`强度时我们**除了<>号之外**，都允许出现。当为`strong`强度时，我们只允许出现常用**汉字，数字，字母**。内置的注解对此则无能为力，我们试着通过自定义校验来解决这个问题。


场景：要校验`showStatus`的0/1状态，可以用正则，但我们可以利用其他方式解决复杂场景。比如我们想要下面的场景

```java
/**
	 * 显示状态[0-不显示；1-显示]
	 */
@NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
@ListValue(vals = {0,1}, groups = {AddGroup.class, UpdateGroup.class, UpdateStatusGroup.class})
private Integer showStatus;
```

添加依赖

```xml
<!--校验-->
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>2.1.0.Final</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>5.4.1.Final</version>
</dependency>
<!--高版本需要javax.el-->
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.el</artifactId>
    <version>3.0.1-b08</version>
</dependency>
```



### 1、自定义校验注解

必须有3个属性

- message()错误信息
- groups()分组校验
- payload()自定义负载信息

```java
// 自定义注解
@Documented
@Constraint(validatedBy = { ListValueConstraintValidator.class}) // 校验器
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE }) // 哪都可以标注
@Retention(RUNTIME)
public @interface ListValue {
    // 使用该属性去Validation.properties中取
    String message() default "{com.atguigu.common.valid.ListValue.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    // 数组，需要用户自己指定
    int[] value() default {};
}
```

因为上面的message值对应的最终字符串需要去ValidationMessages.properties中获得，所以我们在common中新建文件`ValidationMessages.properties`

文件内容

```properties
com.atguigu.common.valid.ListValue.message=必须提交指定的值 [0,1]
```



### 2、自定义校验器ConstraintValidator

上面只是定义了异常消息，但是怎么验证是否异常还没说，下面的ConstraintValidator就是说的

比如我们要限定某个属性值必须在一个给定的集合里，那么就通过重写initialize()方法，指定可以有哪些元素。

而controller接收到的数据用isValid(验证

```java
public class ListValueConstraintValidator 
    implements ConstraintValidator<ListValue,Integer> { //<注解,校验值类型>
    
    // 存储所有可能的值
    private Set<Integer> set=new HashSet<>();
    
    @Override // 初始化，你可以获取注解上的内容并进行处理
    public void initialize(ListValue constraintAnnotation) {
        // 获取后端写好的限制 // 这个value就是ListValue里的value，我们写的注解是@ListValue(value={0,1})
        int[] value = constraintAnnotation.value();
        for (int i : value) {
            set.add(i);
        }
    }

    @Override // 覆写验证逻辑
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 看是否在限制的值里
        return  set.contains(value);
    }
}
```

具体的校验类需要实现`ConstraintValidator`接口，第一个泛型参数是所对应的校验注解类型，第二个是校验对象类型。在初始化方法`initialize`中，我们可以先做一些别的初始化工作，例如这里我们获取到注解上的value并保存下来，然后生成set对象。

真正的验证逻辑由`isValid`完成，如果传入形参的属性值在这个set里就返回true，否则返回false

### 3、关联校验器和校验注解

```java
@Constraint(validatedBy = { ListValueConstraintValidator.class})
```

一个校验注解可以匹配多个校验器

### 4、使用实例

```java
	/**
	 * 显示状态[0-不显示；1-显示]
	  用value[]指定可以写的值
	 */
	@ListValue(value = {0,1},groups ={AddGroup.class})
	private Integer showStatus;
```

> 如验证手机号格式，可以参考https://blog.csdn.net/GAMEloft9/article/details/81699500

## 21. 商品SPU和SKU管理

重新执行“sys_menus.sql”

- SPU：standard product unit(标准化产品单元)：是商品信息聚合的最小单位，是一组**可复用、易检索**的标准化信息的集合，该集合描述了一个产品的特性。
  - 如iphoneX是SPU
- SKU：stock keeping unit(库存量单位)：库存进出计量的基本单元，可以是件/盒/托盘等单位。SKU是对于大型连锁超市DC配送中心物流管理的一个必要的方法。现在已经被引申为产品统一编号的简称，每种产品对应有唯一的SKU号。
  - 如iphoneX 64G 黑色 是SKU 



- 基础属性：同一个SPU拥有的特性叫**基本属性**。如机身长度，这个是手机共用的属性。而每款手机的属性值不同
  - 也可以叫规格参数
- 销售属性：能决定库存量的叫**销售属性**。如颜色



3、基本属性〖规格参数〗与销售属性
每个分类下的商品共享规格参数，与销售属性。只是有些商品不一定要用这个分类下全部的属性；


- 属性是以三级分类组织起来的
- 规格参数中有些是可以提供检索的
- **规格参数**也是**基本属性**，他们具有自己的分组
- 属性的分组也是以三级分类组织起来的
- 属性名确定的，但是值是每一个商品不同来决定的

#### ==pms数据库表==

pms数据库下的attr属性表，attr-group表

- attr-group-id：几号分组
- catelog-id：什么类别下的，比如手机

根据商品找到spu-id，attr-id

属性关系-规格参数-销售属性-三级分类     关联关系

> 每个分类有特点的属性

先通过分类找打对应的属性分组，然后根据属性分组查到拥有的属性。

> 一个分类可以有多个属性数组，一个属性分组可以有多个属性

![](https://i0.hdslb.com/bfs/album/c54c5509f5a407f3505d2eec3b8d6f149824b63e.png)

SPU-SKU属性表

![](https://i0.hdslb.com/bfs/album/5b71d021497abcb2947057a25d4f0f9149da9ad3.png)

荣耀V20有两个属性，网络和像素，但是这两个属性的spu是同一个，代表是同款手机。

sku表里保存spu是同一手机，sku可能相同可能不同，相同代表是同一款，不同代表是不同款。

![](https://i0.hdslb.com/bfs/album/a0343be3bb1e76551588fb3aaa4937197f1c622a.png)

属性表说明每个属性的 枚举值



分类表有所有的分类，但有父子关系

## 22. 属性分组

点击子组件，父组件触发事件

> 前端代码不自己编写了，复制/代码/前端/modules/文件夹里面的内容复制到vs中
>
> 如果左侧显示没有视频全，是因为没有执行sys_menus.sql

##### 接口文档地址

https://easydoc.xyz/s/78237135

#### 属性分组

后台：商品系统/平台属性/属性分组

现在想要实现点击菜单的左边，能够实现在右边展示数据

![image-20200430215649355](https://i0.hdslb.com/bfs/album/fcdd3ecbf3485731ab21284af6e0cde83b8b6b36.png)



根据其他的请求地址http://localhost:8001/#/product-attrgroup

所以应该有product/attrgroup.vue。我们之前写过product/cateory.vue，现在我们要抽象到common/cateory.vue（也就是左侧的tree单独成一个vue组件）

1）左侧内容：

要在左面显示菜单，右面显示表格。复制`<el-row :gutter="20">。。。`，放到attrgroup.vue的`<template>`。20表示列间距

去element-ui文档里找到布局，

```vue
<el-row :gutter="20">
    <el-col :span="6"> <div class="grid-content bg-purple"></div></el-col>
    <el-col :span="18"><div class="grid-content bg-purple"></div></el-col>
</el-row>
```

分为2个模块，分别占6列和18列（分别是tree和当前spu等信息）

有了布局之后，要在里面放内容。接下来要抽象一个分类vue。新建common/category，生成vue模板。把之前写的el-tree放到`<template>`

```vue
<el-tree :data="menus" 
         :props="defaultProps" node-key="catId" ref="menuTree" @node-click="nodeClick"	></el-tree>
所以他把menus绑定到了菜单上，
所以我们应该在export default {中有menus的信息
该具体信息会随着点击等事件的发生会改变值（或比如created生命周期时），
tree也就同步变化了
```

common/category写好后，就可以在attrgroup.vue中导入使用了

```vue
<script>
import Category from "../common/category";
export default {
  //import引入的组件需要注入到对象中才能使用。组件名:自定义的名字，一致可以省略
  components: { Category},
```

导入了之后，就可以在`attrgroup.vue`中找合适位置放好

```vue
<template>
<el-row :gutter="20">
    <el-col :span="6">
        <category @tree-node-click="treenodeclick"></category>
    </el-col>
```

2）右侧表格内容：

开始填写属性分组页面右侧的表格

复制gulimall-product\src\main\resources\src\views\modules\product\attrgroup.vue中的部分内容div到`attrgroup.vue`

批量删除是弹窗add-or-update

导入data、结合components

#### 父子组件

要实现功能：点击左侧，右侧表格对应内容显示。



父子组件传递数据：category.vue点击时，引用它的attgroup.vue能感知到， 然后通知到add-or-update

比如嵌套div，里层div有事件后冒泡到外层div（是指一次点击调用了两个div的点击函数）

1）子组件（category）给父组件（attrgroup）传递数据，事件机制；

去element-ui的tree部分找event事件，看node-click()

在category中绑定node-click事件，

```vue
<el-tree :data="menus" :props="defaultProps" node-key="catId" ref="menuTree" 
         @node-click="nodeClick"	></el-tree>
```

#### this.$emit()

2）子组件给父组件发送一个事件，携带上数据；

```javascript
nodeClick(data,Node,component){
    console.log("子组件被点击",data,Node,component);
    this.$emit("tree-node-click",data,Node,component);
}, 
    第一个参数事件名字随便写，
    后面可以写任意多的东西，事件发生时都会传出去
```

this.$emit(事件名,"携带的数据");

3）父组件中的获取发送的事件

```vue
在attr-group中写
<category @tree-node-click="treeNodeClick"></category>
表明他的子组件可能会传递过来点击事件，用自定义的函数接收传递过来的参数
```

```javascript
 父组件中进行处理
//获取发送的事件数据
    treeNodeClick(data,Node,component){
     console.log("attgroup感知到的category的节点被点击",data,Node,component);
     console.log("刚才被点击的菜单ID",data.catId);
    },
```



## 23、按接口文档开发

https://easydoc.xyz/s/78237135/ZUqEdvA4/OXTgKobR

> 关于接口文档，可以百度了解下Swagger

#### 查询功能：

GET     /product/attrgroup/list/{catelogId}

按照这个url，去product项目下的`attrgroup-controller`里修改

```java
/**
     * 列表
     * @param  catelogId 0的话查所有
     */
@RequestMapping("/list/{catelogId}")
public R list(@RequestParam Map<String, Object> params,
              @PathVariable Long catelogId){
    //        PageUtils page = attrGroupService.queryPage(params);
    PageUtils page = attrGroupService.queryPage(params,catelogId);
    return R.ok().put("page", page);
}
```



增加接口与实现

- Query里面就有个方法getPage()，传入map，将map解析为mybatis-plus的`IPage<T>`对象
- 自定义PageUtils类用于传入IPage<T>对象，得到其中的分页信息
-  AttrGroupServiceImpl extends ServiceImpl，其中ServiceImpl的父类中有方法page(IPage, Wrapper<T>)。对于wrapper而言，没有条件的话就是查询所有
- queryPage()返回前还会return new PageUtils(page);，把page对象解析好页码信息，就封装为了响应数据

```JAVA
public class AttrGroupServiceImpl 
    extends ServiceImpl<AttrGroupDao, AttrGroupEntity>
    implements AttrGroupService {


    @Override // 根据分类返回属性分组 // AttrGroupServiceImpl.java // 按关键字或者按id查
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        // select * from AttrGroup where attr_group_id='key' or attr_group_name like 'key'
        if(!StringUtils.isEmpty(key)){
            // 传入consumer
            wrapper.and((obj)->
                        obj.eq("attr_group_id", key).or().like("attr_group_name", key)
                       );
        }

        if(catelogId == 0){//  0的话查所有
            // Query可以把map封装为IPage // this.page(IPage,QueryWrapper)
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),// Query自己封装方法返回Page对象
                                                    wrapper);
            return new PageUtils(page);
        }else {
            wrapper.eq("catelog_id",catelogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                                                    wrapper);
            return new PageUtils(page);
        }
    }
```

测试

查1分类的属性分组：localhost:88/api/product/attrgroup/list/1

查1分类的属性分组并且分页、关键字为aa：localhost:88/api/product/attrgroup/list/1?page=1&key=aa。结果当然查不到

```json
{
    "msg": "success",
    "code": 0,
    "page": {
        "totalCount": 0,
        "pageSize": 10,
        "totalPage": 0,
        "currPage": 1,
        "list": []
    }
}
```

然后调整前端

发送请求时url携带id信息，${this.catId}，get请求携带page信息

点击第3级分类时才查，修改attr-group.vue中的函数即可

```js
//感知树节点被点击
treenodeclick(data, node, component) {
    if (node.level == 3) {
        this.catId = data.catId;
        this.getDataList(); //重新查询
    }
},
    
// 获取数据列表
getDataList() {
    this.dataListLoading = true;
    this.$http({
        url: this.$http.adornUrl(`/product/attrgroup/list/${this.catId}`),
        method: "get",
        params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize,
            key: this.dataForm.key
        })
    }).then(({ data }) => {
        if (data && data.code === 0) {
            this.dataList = data.page.list;
            this.totalPage = data.page.totalCount;
        } else {
            this.dataList = [];
            this.totalPage = 0;
        }
        this.dataListLoading = false;
    });
},
```

#### 新增属性分组

上面演示了查询功能，下面写insert分类

但是想要下面这个效果：

因为分类可以对应多个属性分组，所以我们新增的属性分组时要指定分类

<img src="https://i0.hdslb.com/bfs/album/7c09306e00886f1bdc7a9763b3193c0c8bd2660e.png" style="zoom:67%;" />

下拉菜单应该是手机一级分类的，这个功能是级联选择器

#### 级联选择器<el-cascader

级联选择：https://element.eleme.cn/#/zh-CN/component/cascader

级联选择的下拉同样是个options数组，多级的话用children属性即可

> 只需为 Cascader 的`options`属性指定选项数组即可渲染出一个级联选择器。通过`props.expandTrigger`可以定义展开子级菜单的触发方式。

去vue里找src\views\modules\product\attrgroup-add-or-update.vue

修改对应的位置为`<el-cascader 。。。>`

把data()里的数组categorys绑定到options上即可，更详细的设置可以用props绑定

##### @JsonInclude去空字段

优化：没有下级菜单时不要有下一级空菜单，在java端把children属性空值去掉，空集合时去掉children字段，

可以用`@JsonInclude(Inlcude.NON_EMPTY)`注解标注在实体类的属性上，

```java
@TableField(exist =false)
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 不为空时返回的json才带该字段
private List<CategoryEntity> children;
```

提交完后返回页面也刷新了，是用到了父子组件。在`$message`弹窗结束回调`$this.emit`

接下来要解决的问题是，修改了该vue后，新增是可以用，修改回显就有问题了，应该回显3级

P73完

##### 修改属性分组

要的效果如图所示

![](https://i0.hdslb.com/bfs/album/ab751b91f624060468afb87050546a3075d534be.png)

```html
<el-button
           type="text"
           size="small"
           @click="addOrUpdateHandle(scope.row.attrGroupId)"
           >修改</el-button>

<script>
    // 新增 / 修改
    addOrUpdateHandle(id) {
        // 先显示弹窗
        this.addOrUpdateVisible = true;
        // .$nextTick(代表渲染结束后再接着执行
        this.$nextTick(() => {
            // this是attrgroup.vue
            // $refs是它里面的所有组件。在本vue里使用的时候，标签里会些ref=""
            // addOrUpdate这个组件
            // 组件的init(id);方法
            this.$refs.addOrUpdate.init(id);
        });
    },
</script>
在init方法里进行回显
但是分类的id还是不对，应该是用数组封装的路径
```

根据属性分组id查到属性分组后填充到页面

```js
init(id) {
    this.dataForm.attrGroupId = id || 0;
    this.visible = true;
    this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.attrGroupId) {
            this.$http({
                url: this.$http.adornUrl(
                    `/product/attrgroup/info/${this.dataForm.attrGroupId}`
                ),
                method: "get",
                params: this.$http.adornParams()
            }).then(({ data }) => {
                if (data && data.code === 0) {
                    this.dataForm.attrGroupName = data.attrGroup.attrGroupName;
                    this.dataForm.sort = data.attrGroup.sort;
                    this.dataForm.descript = data.attrGroup.descript;
                    this.dataForm.icon = data.attrGroup.icon;
                    this.dataForm.catelogId = data.attrGroup.catelogId;
                    //查出catelogId的完整路径
                    this.catelogPath =  data.attrGroup.catelogPath;
                }
            });
        }
    });
```

修改AttrGroupEntity

```java
/**
	 * 三级分类修改的时候回显路径
	 */
@TableField(exist = false) // 数据库中不存在
private Long[] catelogPath;
```

修改controller，找到属性分组id对应的分类，然后把该分类下的所有属性分组都填充好

```java
/**
     * 信息
     */
@RequestMapping("/info/{attrGroupId}")
//@RequiresPermissions("product:attrgroup:info")
public R info(@PathVariable("attrGroupId") Long attrGroupId){
    AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
    // 用当前当前分类id查询完整路径并写入 attrGroup
    Long[] paths= categoryService.findCateLogPath(attrGroup.getCatelogId())
    attrGroup.setCatelogPath(paths);
    return R.ok().put("attrGroup", attrGroup);
}
```

添加service，

```java
@Override // CategoryServiceImpl
public Long[] findCateLogPath(Long catelogId) {
    List<Long> paths = new ArrayList<>();
    paths = findParentPath(catelogId, paths);
    // 收集的时候是顺序 前端是逆序显示的 所以用集合工具类给它逆序一下
    // 子父 转 父子
    Collections.reverse(paths);
    return paths.toArray(new Long[paths.size()]); // 1级  2级  3级
}
/**
     * 递归收集所有父分类
     */
private List<Long> findParentPath(Long catlogId, List<Long> paths) {
    // 1、收集当前节点id
    paths.add(catlogId);// 比如父子孙层级，返回的是 孙 子 父
    CategoryEntity parent_Id = this.getById(catlogId);
    if (byId.getParentCid() != 0) {
        // 递归
        findParentPath(parent_Id.getParentCid(), paths);
    }
    return paths;
}
```

优化：会话关闭时清空内容，防止下次开启还遗留数据

##### bug解决：三级菜单只显示一级的问题

这个大概是因为你复制了别人的github代码，而CategoryController他的controller没有写好。我当然图省事复制了一段代码，结果controller和service层都写了重复的逻辑。

正确方法是把controller逻辑去掉，直接返回即可。

下面的代码是返回父类信息，是父子结点的关系

```java
@Override // service层
public List<CategoryEntity> listWithTree() {
    // 怎么拿categoryDao？
    /*
        * 继承了ServiceImpl<CategoryDao, CategoryEntity>
        有个属性baseMapper，自动注入
        * */

    // 1 查出所有分类
    List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
    // 2 组装成父子的树型结构
    // 2.1 找到所有一级分类
    List<CategoryEntity> level1Menus = categoryEntities.stream().filter(
        // 找到一级
        categoryEntity -> categoryEntity.getParentCid() == 0
    ).map(menu->{
        // 把当前的child属性改了之后重新返回
        menu.setChildren(getChildren(menu,categoryEntities));
        return menu;
    }).sorted((menu1,menu2)->
              menu1.getSort()-menu2.getSort()).collect(Collectors.toList());

    return level1Menus;
    //        return categoryEntities;
}
```

```java
@RequestMapping("/list/tree")
public R list(){
    List<CategoryEntity> entities = categoryService.listWithTree();

    return R.ok().put("data", entities);
}
```

## 24、分页插件

P75

#### mybatis-plus用法

官网：https://mp.baomidou.com/guide/page.html

个人简要mybatis-plus笔记：https://blog.csdn.net/hancoder/article/details/113787197

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version></version>
</dependency>
```

在 Spring Boot 启动类中添加 `@MapperScan` 注解，扫描 Mapper 文件夹：

> 注意mybatis-plus中无需像mybatis一样在接口上加@Mapper注解

新建mapper类

```java
public interface UserMapper 
    extends BaseMapper<User> { // 实现BaseMapper< >

}
```

执行查询

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        // 方法是mp自动生成的
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
}
```

##### mp常用注解

比如@TableName，标注在实体类上，使用的时候定义mapper接口指定实体类泛型即可

也可以使用@TableField映射属性和数据库字段

@TableLogic用于逻辑删除

##### wrapper

查询条件用QueryWrapper包装

wrapper.allEq(map);用于指定字段值

 wrapper.gt("age",2);// 大于  // 用于指定字段与常数关系

```java
QueryWrapper wrapper = new QueryWrapper();
wrapper.orderByDesc("age");
wrapper.orderByAsc("age");
wrapper.having("id > 8");
mapper.selectList(wrapper).forEach(System.out::println);

mapper.selectBatchIds(Arrays.asList(7,8,9));
```

#### mp分页使用

> mp自带的分页是内存分页，性能低，所以需要手动写分页配置，使用物理分页

需要先添加个mybatis的拦截器

```java
package com.atguigu.gulimall.product.config;


@EnableTransactionManagement
@MapperScan("com.atguigu.gulimall.product.dao")
@Configuration
public class MybatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(1000);
        return paginationInterceptor;
    }
}
```

- 接口`IPage<User> selectPageVo(Page<?> page, Integer state);`
- xml：不变
- 接收的返回值`IPage<T> `

```java
Page page = new Page<>(2,2);
Page result = mapper.selectPage(page,null);
result.getRecords()
```

> 如果要自定义SQL，在接口里单独写@Select注解或者在xml里写好即可

#####  Query

在Service实现层 this.page(Page,QueryWrapper)

项目中用的分页方式，不是自己创建page对象，而是根据url的参数自动封装

```java
package com.atguigu.common.utils;

public class Query<T> {

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params,  // 参数有curPage limit order  sidx  asc
                            String defaultOrderField,// 默认排序字段
                            boolean isAsc) { // 默认降序
        //分页参数
        long curPage = 1;
        long limit = 10;
        // new Page<>(curPage, limit);   .
        // page.addOrder(OrderItem.asc(orderField));
        // page.addOrder(OrderItem.desc(orderField));
        // page.addOrder(OrderItem.asc(defaultOrderField));
        // page.addOrder(OrderItem.desc(defaultOrderField));

        // 页码
        if(params.get(Constant.PAGE) != null){
            curPage = Long.parseLong((String)params.get(Constant.PAGE));
        }
        // 偏移
        if(params.get(Constant.LIMIT) != null){
            limit = Long.parseLong((String)params.get(Constant.LIMIT));
        }

        // 分页对象  mybatis-plus内容，实现Ipage
        Page<T> page = new Page<>(curPage, limit);

        // 分页参数
        params.put(Constant.PAGE, page);

        // 排序字段
        // 防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject((String)params.get(Constant.ORDER_FIELD));
        String order = (String)params.get(Constant.ORDER);


        // 前端字段排序
        if(StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)){
            if(Constant.ASC.equalsIgnoreCase(order)) {
                return  page.addOrder(OrderItem.asc(orderField));
            }else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }
        // 如果已经传来了排序字段，已经返回了

        // 没有排序字段，则不排序
        if(StringUtils.isBlank(defaultOrderField)){
            return page;
        }

        // 默认排序
        if(isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        }else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }
}

```



#### 常规用法：XML 自定义分页

> 这种用法其实是mybatis的内容

- UserMapper.java 方法内容

```java
public interface UserMapper {//可以继承或者不继承BaseMapper
    /**
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param state 状态
     * @return 分页对象
     */
    IPage<User> selectPageVo(Page<?> page, Integer state);
}
```

- UserMapper.xml 等同于编写一个普通 list 查询，mybatis-plus 自动替你分页

```xml
<select id="selectPageVo" resultType="com.baomidou.cloud.entity.UserVo">
    SELECT id,name FROM user WHERE state=#{state}
</select>
```

- UserServiceImpl.java 调用分页方法

```java
public IPage<User> selectUserPage(Page<User> page, Integer state) {
    // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
    // page.setOptimizeCountSql(false);
    // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
    // 要点!! 分页返回的对象与传入的对象是同一个
    return userMapper.selectPageVo(page, state);
}
```

### 模糊查询

```java
@Override // BrandServiceImpl
public PageUtils queryPage(Map<String, Object> params) {
    QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
    String key = (String) params.get("key");
    if(!StringUtils.isEmpty(key)){
        // 字段等于  or  模糊查询
        wrapper.eq("brand_id", key).or().like("name", key);
    }
    // 按照分页信息和查询条件  进行查询
    IPage<BrandEntity> page = this.page(
        // 传入一个IPage对象，他是接口，实现类是Page
        new Query<BrandEntity>().getPage(params),
        wrapper
    );
    return new PageUtils(page);
}
```

#### Ipage

```JAVA
// Page对象指定页码和条数，其中的泛型是数据类型

// this.page()是Iservice里的方法
default <E extends IPage<T>> E page(E page,
                                    Wrapper<T> queryWrapper) {
    return this.getBaseMapper().selectPage(page, queryWrapper);
}
```



## 25、关联分类/商品

新增的华为、小米、oppo都应该是手机下的品牌，但是品牌对分类可能是一对多的，比如小米对应手机和电视

多对多的关系应该有relation表

修改CategoryBrandRelationController的逻辑

API：https://easydoc.xyz/doc/75716633/ZUqEdvA4/SxysgcEF

> 一个商品可以有多个分类，分组可以有多个属性分组

```java
/**
     * 获取当前品牌的所有分类列表
     */
@GetMapping("/catelog/list")
public R list(@RequestParam("brandId") Long brandId){
    // 根据品牌id获取其分类信息
    List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(
        new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId)
    );
    return R.ok().put("data", data);
}
// 获得分类列表后再继续进行后面的工作
```

##### 关联表的优化：

分类名本可以在brand表中，但因为**关联查询对数据库性能有影响**，在电商中大表数据从不做关联，哪怕**分步查**也不用关联

所以像name这种冗余字段可以保存，优化save，**保存时用关联表存好，但select时不用关联**

```java
@RequestMapping("/save")
public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
    categoryBrandRelationService.saveDetail(categoryBrandRelation);

    return R.ok();
}
```

```java
/**
     * 根据获取品牌id 、三级分类id查询对应的名字保存到数据库
     */
@Override // CategoryBrandRelationServiceImpl
public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
    // 获取品牌id 、三级分类id
    Long brandId = categoryBrandRelation.getBrandId();
    Long catelogId = categoryBrandRelation.getCatelogId();
    // 根据id查 品牌名字、分类名字，统一放到一个表里，就不关联分类表查了
    BrandEntity brandEntity = brandDao.selectById(brandId);
    CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
    // 把查到的设置到要保存的哪条数据里
    categoryBrandRelation.setBrandName(brandEntity.getName());
    categoryBrandRelation.setCatelogName(categoryEntity.getName());
    this.save(categoryBrandRelation);
}
```

最终效果：

![](https://i0.hdslb.com/bfs/album/8cf7bc109c0e8dbc19142b4d17fa4472e339bf3a.png)

##### 保持冗余字段的数据一致

但是如果分类表里的name发送变化，那么品牌表里的分类name字段应该同步变化。

所以应该修改brand-controller，使之update时检测分类表里的name进行同步





属性分组

> 属性分组是说某一分类锁拥有的属性分组（每个组里还有细节属性），即`List<List<属性>>`

P76

问题：查询所有时没有模糊查询

还是像之前一样解决一些问题

http://localhost:8001/#/product-baseattr    新增关联

获取属性分组的关联的所有属性

属性分组API：https://easydoc.xyz/doc/75716633/ZUqEdvA4/LnjzZHPj

发送请求：/product/attrgroup/{attrgroupId}/attr/relation



获取当前属性分组所关联的属性

![1588766303205](https://i0.hdslb.com/bfs/album/e7d4a99e91b4a5c32edeb91f3c2844ea0686eb96.png)



如何查找：既然给出了attr_group_id，那么到中间表中查询出来所关联的attr_id，然后得到最终的所有属性即可。

可能出现null值的问题，提前返回null

关联属性的时候让他显示未关联的属性，而且还要只显示分组内的属性  /product/attrgroup/{attrgroupId}/noattr/relation



## 26. 规格参数新增与VO

规格参数新增时，请求的URL：Request URL: 

http://localhost:88/api/product/attr/base/list/0?t=1588731762158&page=1&limit=10&key=

当有新增字段时，我们往往会在entity实体类中新建一个字段，并标注数据库中不存在该字段

```java
如在一些Entity中，为了让mybatis-plus与知道某个字段不与数据库匹配，
    那么就加个
    @TableField(exist=false)
    private Long attrGroupId;
```

然而这种方式并不规范，比较规范的做法是，新建一个`vo`文件夹，将每种不同的对象，按照它的功能进行了划分。在java中，涉及到了这几种类型

PO、DO、TO、DTO

1．PO持久对象

>  PO就是对应数据库中某个表中的一条记录，多个记录可以用PO的集合。PO中应该不包含任何对数据的操作。

2、DO（Domain 0bject)领域对象

> 就是从现实世界中推象出来的有形或无形的业务实体。

3.TO(Transfer 0bject)，数据传输对象传输的对象

> 不同的应用程序之间传输的对象。微服务

4.DTO(Data Transfer Obiect)数据传输对象

> 这个概念来源于J2EE的设汁模式，原来的目的是为了EJB的分布式应用握供粗粒度的数据实体，以减少分布式调用的次数，从而握分布式调用的性能和降低网络负载，但在这里，泛指用于示层与服务层之间的数据传输对象。

5.VO(value object)值对象

> 通常用于业务层之间的数据传递，和PO一样也是仅仅包含数据而已。但应是抽象出的业务对象，可以和表对应，也可以不，这根据业务的需要。用new关韃字创建，由GC回收的

View object：视图对象

接受页面传递来的对象，封装对象

将业务处理完成的对象，封装成页面要用的数据

6.BO(business object)业务对象

> 从业务模型的度看．见IJML元#领嵫模型的领嵫对象。封装业务逻辑的java对象，通过用DAO方法，结合PO,VO进行业务操作。businessobject:业务对象主要作用是把业务逻辑封装为一个对苤。这个对象可以包括一个或多个其它的对彖。比如一个简历，有教育经历、工怍经历、社会关系等等。我们可以把教育经历对应一个PO工作经历

7、POJO简单无规则java对象

8、DAO

##### 新建VO对象

Request URL: http://localhost:88/api/product/attr/save，现在的情况是，它在保存的时候，只是保存了attr，并没有保存attrgroup，为了解决这个问题，我们新建了一个`vo/AttrVo.java`，在原`Attr`基础上增加了attrGroupId字段，使得保存新增数据的时候，也保存了它们之间的关系。

通过" `BeanUtils.copyProperties(attr,attrEntity);`"能够实现在两个Bean之间属性对拷

```java 
@Transactional
@Override
public void saveAttr(AttrVo attrVo) {
    AttrEntity attrEntity = new AttrEntity();
    // 重要的工具
    BeanUtils.copyProperties(attrVo, attrEntity);
    //1、保存基本数据
    this.save(attrEntity);
    //2、保存关联关系
    if (attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attrVo.getAttrGroupId() != null) {
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
        relationEntity.setAttrId(attrEntity.getAttrId());
        relationEntity.setAttrSort(0);
        relationDao.insert(relationEntity);
    }
}
```

问题：现在有两个查询，一个是查询部分，另外一个是查询全部，但是又必须这样来做吗？还是有必要的，但是可以在后台进行设计，两种查询是根据catId是否为零进行区分的。

##### BeanUtils.copyProperties(attr,attrEntity);

这个是spring的工具类，用于拷贝同名属性

#### 属性分页

先用mp的正常分页查出来数据，得到Page对象

然后用PageUtils把分页信息得到，但里面的数据需要替换一下

替换数据是为了解决“不使用联表查询”

- 查询的key是分类，

```java

/**
     *
     * 分页模糊查询  ，比如按分类查属性、按属性类别查属性
     */
@Override // AttrServiceImpl.java
public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId,
                                   String attrType) { // 表明查询的是 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
    // 传入的attrType是"base"或其他，但是数据库存的是 "0"销售 / "1"基本
    // 属性都在pms_attr表中混合着
    QueryWrapper<AttrEntity> wrapper =
        new QueryWrapper<AttrEntity>().eq("attr_type", "base".equalsIgnoreCase(attrType)
                                          ?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                                          :ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

    // 如果参数带有分类id，则按分类查询
    if (catelogId != 0L ) {
        wrapper.eq("catelog_id", catelogId);
    }
    // 支持模糊查询，用id或者name查
    String key = (String) params.get("key");
    if (!StringUtils.isEmpty(key)) {
        wrapper.and((w) -> {
            w.eq("attr_id", key).or().like("attr_name", key);
        });
    }
    // 正式查询满足条件的属性
    IPage<AttrEntity> page = this.page(
        new Query<AttrEntity>().getPage(params),
        wrapper
    );
    List<AttrEntity> records = page.getRecords();
    PageUtils pageUtils = new PageUtils(page);

    // 查到属性后还要结合分类名字、分组名字(分类->属性->分组) 封装为AttrRespVo对象
    List<AttrRespVo> attrRespVos = records.stream().map((attrEntity) -> {
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity, attrRespVo);

        // 1.设置分类和分组的名字  先获取中间表对象  给attrRespVo 封装分组名字
        if("base".equalsIgnoreCase(attrType)){ // 如果是规格参数才查询，或者说销售属性没有属性分组，只有分类
            // 根据属性id查询关联表，得到其属性分组
            AttrAttrgroupRelationEntity entity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (entity != null && entity.getAttrGroupId() != null) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(entity);
                // 设置属性分组的名字
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
        }

        // 2.查询分类id 给attrRespVo 封装三级分类名字
        CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
        if (categoryEntity != null) {
            attrRespVo.setCatelogName(categoryEntity.getName());
        }
        return attrRespVo;
    }).collect(Collectors.toList());
    pageUtils.setList(attrRespVos);
    return pageUtils;
}
```

## 27. 发布商品

P83

> 提前说一下，发布商品涉及很多服务，如member、coupon、third-party、网关、nacos、product

<img src="https://i0.hdslb.com/bfs/album/89703a9e3ff3139e6decf82a7cf19f3ac77b2854.png" style="zoom:50%;" />

最终要的效果是：上传成功提示：

![](https://i0.hdslb.com/bfs/album/bb29d6024e356ef534cd7a4fb960e47c45082e66.png)



- 基本信息
- 规则参数
  - 前两步都是spu
- 销售属性
- SKU信息
  - 根据上一步选择的录入价格、标题
- 保存完成

获取所有会员等级：/member/memberlevel/list

API：<https://easydoc.xyz/doc/75716633/ZUqEdvA4/jCFganpf>

开启编写member项目

### guli-member

在“gulimall-gateway”中修改“”文件，添加对于member的路由

```yaml
        - id: gulimall-member
          uri: lb://gulimall-member
          predicates:
            - Path=/api/member/**
          filters:
            - RewritePath=/api/(?<segment>/?.*),/$\{segment}
```



在“gulimall-member”中，创建“bootstrap.properties”文件，内容如下：

```properties
spring.cloud.nacos.config.name=gulimall-member
spring.cloud.nacos.config.server-addr=192.168.137.14:8848
spring.cloud.nacos.config.namespace=795521fa-77ef-411e-a8d8-0889fdfe6964
spring.cloud.nacos.config.extension-configs[0].data-id=gulimall-member.yml
spring.cloud.nacos.config.extension-configs[0].group=DEFAULT_GROUP
spring.cloud.nacos.config.extension-configs[0].refresh=true
```



获取分类关联的品牌：/product/categorybrandrelation/brands/list

API：<https://easydoc.xyz/doc/75716633/ZUqEdvA4/HgVjlzWV>

P85

- 查询所有会员等级
- 查询选中分类 关联的 品牌
- 查询分类下的所有属性分组list（从attr-group表中用分类id查到符合的属性分组），还有属性分组中的所有属性list

P86如果遇到图片上传不成功的问题，

- 检查第三方服务是否启动
- 
- 第三方服务里的oss信息，还有跨域的问题
- 前端src/components/upload/multiUpload.vue，有个action信息，要有自己的oss

<img src="https://i0.hdslb.com/bfs/album/9d34c5ec4b877c3d78fb44b7403a44b4b23662ab.png" style="zoom: 67%;" /><img src="https://i0.hdslb.com/bfs/album/2d58a43f6b37481468432a6151b8a82f8dcd4cc7.png" style="zoom: 67%;" /><img src="https://fermhan.oss-cn-qingdao.aliyuncs.com/img/20210218141001.png" style="zoom:50%;" />

添加json生成的vo

最终保存spu信息：观察下面的步骤与db表

```java
/**
     * 保存所有数据 [33kb左右]
     */
@Transactional
@Override
public void saveSpuInfo(SpuSaveVo vo) {

    // 1.保存spu基本信息 pms_sku_info
    // 插入后id自动返回注入
    this.saveBatchSpuInfo(spuInfoEntity); // this.baseMapper.insert(spuInfoEntity);
    // 此处有分布式id的问题，所以要加事务
    
    // 2.保存spu的表述图片  pms_spu_info_desc
    // 3.保存spu的图片集  pms_sku_images

    // 先获取所有图片
    // 保存图片的时候 并且保存这个是那个spu的图片

    // 4.保存spu的规格属性  pms_product_attr_value
    // 5.保存当前spu对应所有sku信息

    // 1).spu的积分信息 sms_spu_bounds

    skus.forEach(item -> {
        // 2).基本信息的保存 pms_sku_info
        // skuName 、price、skuTitle、skuSubtitle 这些属性需要手动保存

        // 设置spu的品牌id

        // 3).保存sku的图片信息  pms_sku_images
        // sku保存完毕 自增主键就出来了 收集所有图片

        // 4).sku的销售属性  pms_sku_sale_attr_value
        // 5.) sku的优惠、满减、会员价格等信息  [跨库]
    });
}
}
```

#### **商品优惠db表**

- SkuLadderEntity买几件打几折
  - 买几件
  - 打几折
  - 是否参与其他优惠
  - skuId
- SkuFullReductionEntity满多少减多少
  - 满多少
  - 减多少
  - 是否参与其他优惠
  - skuId
- MemberPriceEntity会员价格
  - 会员等级id：memberLevelId
  - MemberPriceEntity
  - 会员价格memberPrice
  - 是否参与其他优惠
  - skuId

> 遇到PubSub问题
>
> ```sh
> # 首先安装pubsub-js
> `npm install --save pubsub-js`
> ```
>
> ```
> # 订阅方组件
> `import PubSub from 'pubsub-js'`
> ```
>
> 该this.PubSub为PubSub。



获取分类下所有分组&关联属性

请求类型：/product/attrgroup/{catelogId}/withattr

请求方式：GET

请求URL：http://localhost:88/api/product/attrgroup/225/withattr?t=1588864569478



> P91
>
> debug时，mysql默认的隔离级别为读已提交，为了能够在调试过程中，获取到数据库中的数据信息，可以调整隔离级别为读未提交：
>
> ```sql
> SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
> ```
>
> 但是它对于当前的事务窗口生效，如果想要设置全局的，需要加上global字段。

主键不是自增的话，需要加`@TableId(type=IdType.INPUT)`

## 28. spu商品管理

#### spu检索

URL：/product/spuinfo/list

API：https://easydoc.xyz/doc/75716633/ZUqEdvA4/9LISLvy7

请求参数

```json
{
   page: 1,//当前页码
   limit: 10,//每页记录数
   sidx: 'id',//排序字段
   order: 'asc/desc',//排序方式
   key: '华为',//检索关键字
   catelogId: 6,//三级分类id
   brandId: 1,//品牌id 
   status: 0,//商品状态
}
```

状态：

- 新建0
- 上架1
- 下架2

当下架时：

```c
t: 1588983789089
status: 2 // 状态
key: 
brandId: 0
catelogId: 0
page: 1
limit: 10
```

> 修改日期：
>
> 问题：在SPU中，写出的日期数据都不符合规则：
>
> ![image-20200509083248660](https://i0.hdslb.com/bfs/album/b741732b4077a85241e0b7408a3d1cf4911264a9.png)
>
> 想要符合规则，可以设置写出数据的规则：
>
> spring.jackson
>
> ```yaml
>spring:
> jackson:
>  date-format: yyyy-MM-dd HH:mm:ss
>   ```
>    

#### SKU检索：

P94

Request URL: http://localhost:88/api/product/skuinfo/list?t=1588989437944&page=1&limit=10&key=&catelogId=0&brandId=0&min=0&max=0

请求体：

```json
t: 1588989437944
page: 1
limit: 10
key: 
catelogId: 0
brandId: 0
min: 0
max: 0
```

API： https://easydoc.xyz/doc/75716633/ZUqEdvA4/ucirLq1D 



## 29. 仓库管理

 库存信息表：

- wms_ware_info   包括仓库所在地区等仓库信息（与商品无关）
- wms_ware_sku：具体商品的库存量和所在仓库
- wms_purchase_detail：采购需求
- wms_purchase：采购单
- wms_ware_order_task：库存工作单
- wms_ware_order_task_detail：库存工作单详情

### gulimall-ware

创建项目后nacos注册、网关重写等

#### 1) 仓库维护

提供模糊查询仓库信息

```java
@RequestMapping("/list")
public R list(@RequestParam Map<String, Object> params){
    PageUtils page = wareInfoService.queryPage(params);

    return R.ok().put("page", page);
}
@Override // WareInfoServiceImpl
public PageUtils queryPage(Map<String, Object> params) { // 传入分页信息
    QueryWrapper<WareInfoEntity> wrapper = new QueryWrapper<>();

    // 查询关键字
    String key = (String) params.get("key");
    if (!StringUtils.isEmpty(key)) {
        // 仓库编号、仓库名字、仓库地址、区域编号
        wrapper.eq("id", key).or().like("name", key).or().like("address", key).or().like("areacode", key);
    }
    // 执行
    IPage<WareInfoEntity> page = this.page(
        new Query<WareInfoEntity>().getPage(params),
        wrapper
    );
    return new PageUtils(page);
}
```

可以打开日志查看查询sql

#### 2) 商品库存

功能：查询sku+库存id+库存数等信息

数据库表：wms_ware_sku 指明每个仓库有什么sku

- 手动新增库存：商品库存页面/新增，然后填写 `sku_id+仓库+库存数`  等信息
- 自动新增库存：需要通过采购完成

#### 3) 采购单维护

##### 3.1) 采购需求

采购需求的生成方式可能有两种：

- 人工新增
- 系统检测到库存量低自动创建

总的流程：

【1】仓库列表功能： 

【2】查询商品库存：

【3】查询采购需求：/ware/purchase/unreceive/list

【4】合并采购需求：

<img src="https://i0.hdslb.com/bfs/album/c46988dcaacd6ce69c864eee8ea959777bb3fb98.png" alt="image-20200509191108806" style="zoom:33%;" />

新建采购需求后还要可以提供合并采购单，比如一个仓库的东西可以合并到一起，让采购人员一趟采购完

<img src="https://i0.hdslb.com/bfs/album/01c00382a0ebec26a2d114121f7554c0e71ba283.png" style="zoom: 33%;" />

然后弹出已有采购单，可以选择一个点确定，格式为（用户:电话号）

<img src="https://i0.hdslb.com/bfs/album/9b7cfe816cc716c52c3da2b4ac2ab6af6b5200f3.png" style="zoom: 33%;" />

合并整单选中parcharseID：POST: http://localhost:88/api/ware/purchase/merge

请求数据：

```json
{
    purchaseId: 1,  # 采购单id，没有携带就新建采购单
    items: [1, 2]   # 采购商品
}
```

> 涉及到两张表：wms_purchase_detail，wms_purchase
>
> 先在采购单中填写数据，然后关联用户，关联用户后，



如果不选择整单直接点击确定，将弹出【没有分配采购人员】提示。

合并整单未选择parcharseID：Request URL: http://localhost:88/api/ware/purchase/merge

<img src="https://i0.hdslb.com/bfs/album/8f59e1273fe76ca152e0a693a8993cbff6f3fc9d.png" alt="image-20200509170916557" style="zoom:50%;" />

```JSON
items: [1, 2]
```



##### 3.2) 采购单

- wms_purchase采购单，里面有创建时间和分类人员等信息
- wms_purchase_detail采购单详情表，指明采购单每项要采购的sku

> 可以自己新增采购单，然后让新的采购需求合并到已有采购单里。
>
> 采购单状态：只有新建、已分配的时候才能合并采购单
>
> - 新建 0
> - 已分配 1
> - 已领取(正在采购) 2
> - 已完成 3
> - 有异常(采购失败) 4

合并采购需求，创建采购单

- 如果没有带过来采购单id，先新建采购单
- 然后修改【采购需求】里对应的【采购单id、采购需求状态】，即purchase_detail表
- 采购需求是purchase_detail表、采购单是purchase表。采购单由多个采购需求组成
- 采购单页面分配采购成功后应该刷新页面，或者说不能重复分配采购需求给不同的采购单（或者说是更新操作）

```java
/**
     * 根据情况修改、创建采购单   [没有更改分配状态]
     */
    @Transactional
    @Override // PurchaseServiceImpl
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        // 如果采购id为null 说明没选采购单，先创建采购单
        if (purchaseId == null) {
            // 新建采购单
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());// 新建状态
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();// 自动返回id
        }

        // 合并采购单 [其实就是修改上面创建的采购单]
        List<Long> items = mergeVo.getItems(); // 获取带过来的采购需求

        // 从数据库查询所有要合并的采购单，然后过滤所有大于 [已分配] 状态的订单，就是说已经去采购了就不能改了
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        // 过滤掉已分配的采购需求
        List<PurchaseDetailEntity> detailEntities =
                detailService.getBaseMapper().selectBatchIds(items).stream()
                        .filter(entity -> {
//                            // 如果正在合并采购异常的项就把这个采购项之前所在的采购单的状态 wms_purchase 表的状态修改为 已分配
//                            if (entity.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()) {
//                                purchaseEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
//                                purchaseEntity.setId(entity.getPurchaseId());
//                                this.updateById(purchaseEntity);
//                            }
                            // 如果没还去采购，就可以更改 // 采购需求有问题可以再去重新采购
                            return entity.getStatus() < WareConstant.PurchaseDetailStatusEnum.BUYING.getCode()
                                    || entity.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode();
                        }).collect(Collectors.toList());
        // 得到过滤好的需求id
        items = detailEntities.stream().map(entity -> entity.getId()).collect(Collectors.toList());
        if (items == null || items.size() == 0) {
            return;
        }
        // 设置仓库id // 采购单得是同个仓库的
        purchaseEntity.setWareId(detailEntities.get(0).getWareId());
        Long finalPurchaseId = purchaseId;
        // 给采购需求设置所属采购单和状态等信息
        List<PurchaseDetailEntity> collect = items.stream().map(item -> {
            PurchaseDetailEntity entity = new PurchaseDetailEntity();
            entity.setId(item);
            entity.setPurchaseId(finalPurchaseId);
            entity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return entity;
        }).collect(Collectors.toList());

        // 更新时间采购单最后更新时间 // 可以通过mp的@TableField(fill=FieldFill.INSERT_UPDATE)来完成，给spring中注入MetaObjectHandler
        detailService.updateBatchById(collect);
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }
```

采购需求状态枚举

```java
public class WareConstant {

    /** 采购单状态枚举 */
    public enum  PurchaseStatusEnum{
        CREATED(0,"新建"),ASSIGNED(1,"已分配"),
        RECEIVE(2,"已领取"),FINISH(3,"已完成"),
        HASERROR(4,"有异常");
        private int code;
        private String msg;

        PurchaseStatusEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }


    /** 采购需求枚举 */
    public enum  PurchaseDetailStatusEnum{
        CREATED(0,"新建"),ASSIGNED(1,"已分配"),
        BUYING(2,"正在采购"),FINISH(3,"已完成"),
        HASERROR(4,"采购失败");
        private int code;
        private String msg;

        PurchaseDetailStatusEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}

```



##### 3.3) 领取采购单

http://localhost:88/api/ware/purchase/received

API：https://easydoc.xyz/doc/75716633/ZUqEdvA4/vXMBBgw1

某个人领取了采购单后，先看采购单是否处于未分配状态，只有采购单是新建或以领取状态时，才更新采购单的状态

后台系统里没有领取采购单这个功能，我们暂时通过postman手动领取采购单

- `POST  localhost:88/api/ware/purchase/received`
- 参数：[1,2,3,4]//采购单id
- 采购单中已经有了人员信息，所以参数中不要带采购人员
- 领取后确认采购单的状态、采购单人员等是否相符
- 更改采购单状态、更改采购需求状态

```java
/**
     * 领取采购单。采购已经有了人员信息。这个请求需要通过post手动完成
     */
@PostMapping("/received")
public R received(@RequestBody List<Long> ids){
    purchaseService.received(ids);

    return R.ok();
}
```

```java
/**
     * 领取采购单
     * ids：采购单id
     * 过滤采购需求，并同步采购需求的状态
     */
@Override
public void received(List<Long> ids) {
    // 没有采购需求直接返回，否则会破坏采购单
    if (ids == null || ids.size() == 0) {
        return;
    }
    // 1.确认当前采购单是已分配状态 // 优化成查询list
    List<PurchaseEntity> purchaseEntityList = this.listByIds(ids);
    purchaseEntityList =
        purchaseEntityList.stream()
        // 只能采购已分配的
        .filter(item -> item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode() || item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode())
        .map(item -> {
            // 设置状态为领取
            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());
    // 2.被领取之后重新设置采购单状态
    this.updateBatchById(purchaseEntityList);

    // 3.改变采购需求状态
    // 打包所有的采购单id(获取过滤后的)
    List<Long> purchaseIdList = purchaseEntityList.stream().map(purchaseEntity -> purchaseEntity.getId()).collect(Collectors.toList());
    System.out.println(purchaseIdList);
    // 通过采购单id查到所有的采购需求(注意这里把所有的采购单需求都混合了，可能不太好)
    //        List<PurchaseDetailEntity> purchaseDetailEntityList =detailService.listDetailByPurchaseId(purchaseIdList);// 这里的参数我都加上了List<Long>
    // 别用eq，得用in
    QueryWrapper<PurchaseDetailEntity> purchase_ids = new QueryWrapper<PurchaseDetailEntity>().in("purchase_id", ids);
    List<PurchaseDetailEntity> purchaseDetailEntityList = detailService.list(purchase_ids);
    System.out.println(purchaseDetailEntityList);
    // 更改采购需求的状态
    purchaseDetailEntityList = purchaseDetailEntityList.stream().map(purchaseDetailEntity -> {
        purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
        return purchaseDetailEntity;
    }).collect(Collectors.toList());
    System.out.println(purchaseDetailEntityList);
    detailService.updateBatchById(purchaseDetailEntityList);
```

注意上面没有按教程来，也就是优化了一下

然后用POSTMAN发送请求模拟APP发送请求

- localhost:88/ap/api/ware/purchase/received   
- body内置为[采购单id]



##### 3.4) 采购完成新增库存

可以多选采购单里哪些采购项（需求）完成了

- 采购项都完成的时候采购单为完成
- 采购项完成时增加库存
- 增加库存时要判断原来是否有库存以区分insert和update
- 加上分页插件

API信息：同样相关页面也省略，通过POSTMAN发送，代表采购员采购回来了，提交信息

- POST      localhost:88/api/ware/purchase/done

- ```json
  {
      id: 6,//采购单id
      items: [  //完成/失败的需求详情
          {itemId:12,status:3,reason:"完成"},
          {itemId:13,status:4,reason:"失败"}
      ]
  }
  ```

后端代码为：

```java
/**
     * 完成采购单
     */
@PostMapping("/done")
public R finish(@RequestBody PurchaseDoneVo doneVo){
    purchaseService.done(doneVo);

    return R.ok();
}
```

```java
@Data
public class PurchaseDoneVo {

	/** 采购单id*/
    @NotNull
    private Long id;

    /** 采购项(需求) */
    private List<PurchaseItemDoneVo> items;
}
```

```java
@Transactional
@Override
public void done(PurchaseDoneVo doneVo) {
    // 1.改变采购单状态
    Long id = doneVo.getId();
    Boolean flag = true;
    List<PurchaseItemDoneVo> items = doneVo.getItems();
    ArrayList<PurchaseDetailEntity> updates = new ArrayList<>();
    double price;
    double p = 0;
    double sum = 0;
    // 2.改变采购项状态
    for (PurchaseItemDoneVo item : items) {
        // 采购失败的情况
        PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
        if (item.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()) {
            flag = false;
            detailEntity.setStatus(item.getStatus());
        } else {
            detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
            // 3.将成功采购的进行入库
            // 查出当前采购项的详细信息
            PurchaseDetailEntity entity = detailService.getById(item.getItemId());
            // skuId、到那个仓库、sku名字
            price = wareSkuService.addStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
            if (price != p) {
                p = entity.getSkuNum() * price;
            }
            detailEntity.setSkuPrice(new BigDecimal(p));
            sum += p;
        }
        // 设置采购成功的id
        detailEntity.setId(item.getItemId());
        updates.add(detailEntity);
    }
    // 批量更新采购单
    detailService.updateBatchById(updates);

    // 对采购单的状态进行更新
    PurchaseEntity purchaseEntity = new PurchaseEntity();
    purchaseEntity.setId(id);
    purchaseEntity.setAmount(new BigDecimal(sum));
    purchaseEntity.setStatus(flag ? WareConstant.PurchaseStatusEnum.FINISH.getCode() : WareConstant.PurchaseStatusEnum.HASERROR.getCode());
    purchaseEntity.setUpdateTime(new Date());
    this.updateById(purchaseEntity);
}
```



##### 3.5) 入库

完成采购增加库存时，需要涉及到设置SKU的name信息到仓库中，这是通过feign远程调用“gulimall-product”服务来实现根据sku_id查询得到sku_name的。

只要异常被捕获，事务是不会滚的（这里需要优化，是高级篇消息队列实现一致性事务的内容）

```java
@Override
public void addStock(Long skuId, Long wareId, Integer skuNum) {

    List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));

    if(wareSkuEntities == null || wareSkuEntities.size() ==0 ){
        // 新增库存
        WareSkuEntity wareSkuEntity = new WareSkuEntity();
        wareSkuEntity.setSkuId(skuId);
        wareSkuEntity.setWareId(wareId);
        wareSkuEntity.setStock(skuNum);
        wareSkuEntity.setStockLocked(0);

        //远程查询SKU的name，若失败无需回滚
        try {
            R info = productFeignService.info(skuId);
            if(info.getCode() == 0){
                Map<String,Object> data=(Map<String,Object>)info.get("skuInfo");
                wareSkuEntity.setSkuName((String) data.get("skuName"));
            }
        } catch (Exception e) {

        }

        wareSkuDao.insert(wareSkuEntity);
    }else{
        // 新增库存 // 乐观锁
        // UPDATE `wms_ware_sku` SET stock = stock + #{skuNum} WHERE sku_id = #{skuId} AND ware_id = #{wareId}
        wareSkuDao.addStock(skuId,wareId,skuNum);
    }
}
// 其实这有多线程问题，
```

新增仓库时多个参数必须用@Param注解标明，这就不啰嗦了

这里时区显示不太正常，可以在配置中添加spring.jackson.time-zone=GMT+8



## 30. 获取spu规格

> 这个部分的内容是说之前创建sku的时候指定了一些基本属性（规格参数），后期怎么修改

GET：/product/attr/base/listforspu/{spuId}

响应：

```json
{
	"msg": "success",
	"code": 0,
	"data": [{
		"id": 43,
		"spuId": 11,
		"attrId": 7,
		"attrName": "入网型号",
		"attrValue": "LIO-AL00",
		"attrSort": null,
		"quickShow": 1
	}]
}
```

#### 修改商品规格

API： https://easydoc.xyz/doc/75716633/ZUqEdvA4/GhnJ0L85 

URL：/product/attr/update/{spuId}

```json
[{
	"attrId": 7,
	"attrName": "入网型号",
	"attrValue": "LIO-AL00",
	"quickShow": 1
}, {
	"attrId": 14,
	"attrName": "机身材质工艺",
	"attrValue": "玻璃",
	"quickShow": 0
}, {
	"attrId": 16,
	"attrName": "CPU型号",
	"attrValue": "HUAWEI Kirin 980",
	"quickShow": 1
}]
```



基础篇完结。



### 笔记不易：

离线笔记均为markdown格式，图片也是云图，10多篇笔记20W字，压缩包仅500k，推荐使用typora阅读。也可以自己导入有道云笔记等软件中

阿里云图床现在**每周得几十元充值**，都要自己往里搭了，麻烦不要散播与转发

![](https://i0.hdslb.com/bfs/album/ff3fb7e24f05c6a850ede4b1f3acc54312c3b0c6.png)

打赏后请主动发支付信息到邮箱  553736044@qq.com  ，上班期间很容易忽略收账信息，邮箱回邮基本秒回

禁止转载发布，禁止散播，若发现大量散播，将对本系统文章图床进行重置处理。

技术人就该干点技术人该干的事



如果帮到了你，留下赞吧，谢谢支持

高级篇见。

ES笔记：[https://blog.csdn.net/hancoder/article/details/113922398](https://blog.csdn.net/hancoder/article/details/113922398)

本项目其他笔记见专栏：[https://blog.csdn.net/hancoder/category_10822407.html](https://blog.csdn.net/hancoder/category_10822407.html)

