package dp.lol.lol_dp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration // Java기반의 설정파일로 선언
public class DatabaseConfig {

    @Bean // spring에 의해 관리되는 빈으로 등록
    @ConfigurationProperties("spring.datasource.hikari")
    // application.yml에서 "spring.datasource.hikari" 접두사를 가진설정 정보를 읽어오기 위한 어노테이션
    // 읽어온 정보를 hikariConfig()메서드에 매핑(바인딩)한다.
    public HikariConfig hikariConfig() {
        // @ConfigurationProperties를 이용해서 가져온 정보를 통해 HikariConfig객체 생성
        return new HikariConfig();
    }


    @Bean
    public DataSource dataSource(HikariConfig hikariConfig) {
        // HikariConfig 객체를 통해 HikariDataSource를 생성한다.
        // 이 데이터 소스는 HikariCP를 사용하여 데이터베이스 연결을 관리하는 데 사용된다.
        // HikariCP는 커넥션 풀(Connection Pool) 라이브러리의 한 종류이다.
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

}
