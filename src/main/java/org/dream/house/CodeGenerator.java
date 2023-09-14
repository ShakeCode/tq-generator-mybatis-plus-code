package org.dream.house;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.dream.house.domain.entity.base.BaseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {

    // TODO 项目名称
    private static final String PROJECT_NAME = "code";

    // TODO 微服务项目模块名（可选，和单体二选一）
    private static final String CLOUD_NAME = "";

    // TODO 路径包名（生成的文件放在绝地路径下，为下面路径代码铺垫）
    private static final String PACKAGE_DIR = "/org/dream/house/";
    // TODO 生成包名
    private static final String PACKAGE_EXE = "org.dream.house";
    // TODO 数据库地址
    private static final String URL = "jdbc:mysql://localhost:5306/promotion?useUnicode=true&characterEncoding =UTF-8&serverTimezone = Asia/Shanghai";
    // TODO 数据库用户名
    private static final String USERNAME = "root";
    // TODO 数据库密码
    private static final String PASSWORD = "root";
    // TODO  作者
    private static final String AUTHOR = "dream";
    // TODO  去除的表前缀（可选）
    private static final String FieldPrefix = "t_";

    // TODO 模板路径（可选）
    private static final String TEMPLATE_PATH = "/templates/entity.java";
    /**
     * 项目路径
     */
    // private static final String PARENT_DIR = System.getProperty("user.dir");
    private static final String PARENT_DIR = "F:/ideaWorkplace/tq-generator-mybatis-plus-code";
    /**
     * xml路径
     */
    //private static final String XML_PATH = PARENT_DIR + "/"+PROJECT_NAME+"-mapper/src/main/resources/mapper"; //单体项目
    private static final String XML_PATH = PARENT_DIR + "/" + CLOUD_NAME + "/src/main/resources/mapper";
    /**
     * entity路径
     */
    //private static final String ENTITY_PATH = PARENT_DIR +"/"+PROJECT_NAME+"-entity/src/main/java"+PACKAGE_DIR+"entity";//单体项目
    private static final String ENTITY_PATH = PARENT_DIR + "/" + CLOUD_NAME + "/src/main/java" + PACKAGE_DIR + "entity";
    /**
     * mapper（Dao）路径
     */
    //private static final String MAPPER_PATH = PARENT_DIR +"/"+PROJECT_NAME+"-mapper/src/main/java"+PACKAGE_DIR+"mapper";//单体项目
    private static final String MAPPER_PATH = PARENT_DIR + "/" + CLOUD_NAME + "/src/main/java" + PACKAGE_DIR + "dao";
    /**
     * service路径
     */
    //private static final String SERVICE_PATH = PARENT_DIR +"/"+PROJECT_NAME+"-service/src/main/java"+PACKAGE_DIR+"service";//单体项目
    private static final String SERVICE_PATH = PARENT_DIR + "/" + CLOUD_NAME + "/src/main/java" + PACKAGE_DIR + "service";
    /**
     * serviceImpl路径
     */
    //private static final String SERVICE_IMPL_PATH = PARENT_DIR +"/"+PROJECT_NAME+"-service/src/main/java"+PACKAGE_DIR+"service/impl/";//单体项目
    private static final String SERVICE_IMPL_PATH = PARENT_DIR + "/" + CLOUD_NAME + "/src/main/java" + PACKAGE_DIR + "service/impl/";
    /**
     * controller路径
     */
    //private static final String CONTROLLER_PATH = PARENT_DIR +"/"+PROJECT_NAME+"-api/src/main/java"+PACKAGE_DIR+"controller";//单体项目
    private static final String CONTROLLER_PATH = PARENT_DIR + "/" + CLOUD_NAME + "/src/main/java" + PACKAGE_DIR + "controller";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                // 全局配置
                .globalConfig(builder -> builder
                        // 设置作者
                        .author(AUTHOR)
                        // 开启swagger注解
                        .enableSwagger()
                        .disableOpenDir()
                )
                // 包配置
                .packageConfig(builder -> builder
                        .parent(PACKAGE_EXE)
                        .xml("mapper")
                        .entity("entity")
                        .mapper("dao")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .pathInfo(getPathInfo())
                )
                // 策略配置
                .strategyConfig((scanner, builder) ->
                                builder.addInclude(getTables(scanner.apply("请输入表名，多个表之间用英文逗号分隔，所有输入all")))
                                        .addTablePrefix(FieldPrefix)
                                        .addTablePrefix("tb_") //设置过滤表前缀
                                        .addTablePrefix("t_") //设置过滤表前缀
                                        .addTablePrefix("tk_") //设置过滤表前缀
                                        .addTablePrefix("dwd_") //设置过滤表前缀
                                        // entity
                                        .entityBuilder()
                                        .fileOverride()
                                        .enableChainModel()
                                        .fileOverride()
                                        .superClass(BaseEntity.class)
                                        .disableSerialVersionUID()
                                        .fileOverride()
                                        .naming(NamingStrategy.underline_to_camel)
                                        .enableChainModel()
                                        .idType(IdType.AUTO)
                                        // 开启lombock
                                        .enableLombok()
                                        // 表字段注解生成
                                        // .enableTableFieldAnnotation()
                                        //.logicDeleteColumnName("deleted")
                                        //.logicDeletePropertyName("deleteFlag")
                                        .idType(IdType.AUTO)
                                        //.addTableFills(new Column("create_time", FieldFill.INSERT))
                                        //.addTableFills(new Property("update_time", FieldFill.INSERT_UPDATE))
                                        // controller
                                        .controllerBuilder()
                                        .fileOverride()
                                        .enableRestStyle()
                                        .formatFileName("%sController")
                                        // service
                                        .serviceBuilder()
                                        .fileOverride()
                                        .superServiceClass(IService.class)
                                        .formatServiceFileName("%sService")
                                        .formatServiceImplFileName("%sServiceImpl")
                                        // mapper
                                        .mapperBuilder()
                                        .fileOverride()
                                        .enableBaseResultMap()
                                        .enableBaseColumnList()
                                        .superClass(BaseMapper.class)
                                        .formatMapperFileName("%sMapper")
                                        .formatXmlFileName("%sMapper")
                        //.enableMapperAnnotation()
                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateConfig(builder -> {
                    // 实体类使用我们自定义模板
//                    builder.entity("/templates/entity.java");
                })
//                .templateEngine(new BeetlTemplateEngine())
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();


    }

    /**
     * 获取路径信息map
     * @author 何翔
     */
    private static Map<OutputFile, String> getPathInfo() {
        Map<OutputFile, String> pathInfo = new HashMap<>(5);
        pathInfo.put(OutputFile.entity, ENTITY_PATH);
        pathInfo.put(OutputFile.mapper, MAPPER_PATH);
        pathInfo.put(OutputFile.service, SERVICE_PATH);
        pathInfo.put(OutputFile.serviceImpl, SERVICE_IMPL_PATH);
        pathInfo.put(OutputFile.controller, CONTROLLER_PATH);
        pathInfo.put(OutputFile.xml, XML_PATH);
        return pathInfo;
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}

