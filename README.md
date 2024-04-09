# resilience4j-learn
Resilience4J demo learn

```yaml
server:
  port: 8080

resilience4j.retry:
  configs:
    default:
      # 최대 시도 횟수
      maxAttempts: 3  # 최대 시도 회수
      # 각 시도마다 대기 시간 설정 (ms)
      waitDuration: 1000
      # retryExceptions에 지정된 예외는 재시도 처리
      retryExceptions:
        - me.ramos.resilience4j.exception.RetryException
      # retryExceptions에 지정되지 않은 예외는 ignoreExceptions로 처리된다.
      ignoreExceptions:
        - me.ramos.resilience4j.exception.IgnoreException
  instances:
    simpleRetryConfig:
      baseConfig: default

resilience4j.circuitbreaker:
  configs:
    default:
      slidingWindowType: COUNT_BASED
      # 최소 7번까진 무조건 CLOSE로 가정하고 호출.
      # 일반적으로 slidingWindowSize와 같게 하며, 그보다 크면 의미가 없다.
      minimumNumberOfCalls: 7
      # minimumNumberOfCalls 이후로 10개의 요청을 기준으로 판단
      slidingWindowSize: 10
      # OPEN 상태에서 HALF_OPEN으로 가려면 얼마나 기다릴 것인지 설정
      waitDurationInOpenState: 10s

      # slidingWindowSize 중 몇 %가 recordException이면 OPEN으로 만들 것인지 설정
      failureRateThreshold: 40

      # 몇 ms 동안 요청이 처리되지 않으면 실패로 간주할 것인지 설정
      slowCallDurationThreshold: 3000
      # slidingWindowSize 중 몇 %가 slowCall이면 OPEN으로 만들지 설정
      slowCallRateThreshold: 60

      # HALF_OPEN 상태에서 5번까진 CLOSE로 가기위해 호출
      permittedNumberOfCallsInHalfOpenState: 5
      # OPEN 상태에서 자동으로 HALF_OPEN으로 갈 것인지 설정
      automaticTransitionFromOpenToHalfOpenEnabled: true

      # actuator를 위한 이벤트 버퍼 사이즈
      eventConsumerBufferSize: 10

      recordExceptions:
        - me.ramos.resilience4j.exception.RecordException
      ignoreExceptions:
        - me.ramos.resilience4j.exception.IgnoreException
  instances:
    simpleCircuitBreakerConfig:
      baseConfig: default

management:
  endpoints.web.exposure.include: '*'
  endpoint.health.show-details: always
  health:
    diskSpace.enabled: false
    circuitbreakers.enabled: true
  metrics.distribution.percentiles-histogram:
    http.server.requests: true
    resilience4j.circuitbreaker.calls: true
```