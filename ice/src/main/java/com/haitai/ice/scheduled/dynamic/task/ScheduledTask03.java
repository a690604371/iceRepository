package com.haitai.ice.scheduled.dynamic.task;

import com.haitai.ice.scheduled.dynamic.service.ScheduledTaskJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务 03
 *
 * @author
 */
public class ScheduledTask03 implements ScheduledTaskJob {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask03.class);

    @Override
    public void run() {
        LOGGER.info("ScheduledTask => 03  run  当前线程名称 {} ", Thread.currentThread().getName());
    }
}