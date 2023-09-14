package org.dream.house;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.dream.house.domain.entity.base.BaseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AppMain {
    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:5306/promotion", "root", "root");

    private static final String basePath = "F:/ideaWorkplace/tq-generator-mybatis-plus-code";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入表名，多个英文逗号分隔？所有输入 all");
        String s1 = scanner.nextLine().trim();
        List<String> tables = getTables(s1);
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((builder) -> builder.author("dream")
                        // 开启swagger注解
                        .enableSwagger()
                        // 不定位目录
                        .disableOpenDir()
                        .outputDir(basePath + "/src/main/java"))
                // 包配置
                .packageConfig((builder) -> builder.parent("org.dream.house")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .mapper("dao")
                        .xml("mapper")
                        .pathInfo(getPathInfo())
                        .entity("entity"))
                // 策略配置
                .strategyConfig((builder) -> builder.addInclude(tables)
                        .addTablePrefix("tb_") //设置过滤表前缀
                        .addTablePrefix("t_") //设置过滤表前缀
                        .addTablePrefix("tk_") //设置过滤表前缀
                        .addTablePrefix("dwd_") //设置过滤表前缀
                        .controllerBuilder()
                        .enableRestStyle()
                        .enableHyphenStyle()
                        .entityBuilder()
                        .enableLombok()
                        // 生成表字段注解
                        // .enableTableFieldAnnotation()
                        .superClass(BaseEntity.class)
                        .disableSerialVersionUID()
                        .fileOverride()
                        .naming(NamingStrategy.underline_to_camel)
                        .enableChainModel()
                        .idType(IdType.AUTO)
                        .build()
                )
                //模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
//                 .templateEngine(new BeetlTemplateEngine())
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private static Map<OutputFile, String> getPathInfo() {
        Map<OutputFile, String> pathInfo = new HashMap<>(5);
        pathInfo.put(OutputFile.xml, basePath + "/src/main/resources/mapper");
        return pathInfo;
    }

    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
