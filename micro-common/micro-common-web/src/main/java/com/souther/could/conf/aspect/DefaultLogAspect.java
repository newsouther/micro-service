package com.souther.could.conf.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by Administrator on 2019/8/8. 参考 https://blog.csdn.net/qq_42255763/article/details/98851389
 */
@Slf4j
public class DefaultLogAspect {

  @Resource
  private ObjectMapper objectMapper;

  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Pointcut("execution(public * com.souther.could.controller.*.*(..))")//切入点描述 这个是controller包的切入点
  public void webLog() {//签名，可以理解成这个切入点的一个名称
  }

  @Before("webLog()") //环绕通知，执行webLog方法
  public void doBefore(JoinPoint joinPoint) throws Throwable {
    // 开始打印请求日志
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    // 获取 @WebLog 注解的描述信息
//        String methodDescription = getAspectLogDescription(joinPoint);

    // 打印请求相关参数
    log.info(
        "========================================== Start ==========================================");
    // 打印请求 url
    log.info("URL            : {}", request.getRequestURL().toString());
    // 打印描述信息
//        log.info("Description    : {}", methodDescription);
    // 打印 Http method
    log.info("HTTP Method    : {}", request.getMethod());
    // 打印调用 controller 的全路径以及执行方法
    log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName());
    // 打印请求的 IP
    log.info("IP             : {}", getRemoteHost(request));

    // 打印请求入参
//    log.info("Request Args   : {}", GsonUtils.gson.toJson(joinPoint.getArgs()));
//    Object args = joinPoint.getArgs();
//    log.info("Request Args   : {}", args);
    log.info("Request Args   : {}", objectMapper.writeValueAsString(joinPoint.getArgs()));
  }

  @After("webLog()")
  public void doAfter() throws Throwable {
    // 接口结束后换行，方便分割查看
    log.info(
        "=========================================== End ===========================================");
  }

  /**
   * 环绕
   *
   * @param proceedingJoinPoint
   * @return
   * @throws Throwable
   */
  @Around("webLog()")
  public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object result = proceedingJoinPoint.proceed();
    // 打印出参
    log.info("Response Args  : {}", objectMapper.writeValueAsString(result));
    // 执行耗时
    log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
    return result;
  }

//    /**
//     * 获取切面注解的描述
//     *
//     * @param joinPoint 切点
//     * @return 描述信息
//     * @throws Exception
//     */
//    public String getAspectLogDescription(JoinPoint joinPoint)
//            throws Exception {
//        String targetName = joinPoint.getTarget().getClass().getName();
//        String methodName = joinPoint.getSignature().getName();
//        Object[] arguments = joinPoint.getArgs();
//        Class targetClass = Class.forName(targetName);
//        Method[] methods = targetClass.getMethods();
//        StringBuilder description = new StringBuilder("");
//        for (Method method : methods) {
//            if (method.getName().equals(methodName)) {
//                Class[] clazzs = method.getParameterTypes();
//                if (clazzs.length == arguments.length) {
//                    description.append(method.getAnnotation(Object.class).description());
//                    break;
//                }
//            }
//        }
//        return description.toString();
//    }

  /**
   * 获取目标主机的ip
   *
   * @param request
   * @return
   */
  private String getRemoteHost(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
  }

}
