/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package org.apache.skywalking.apm.collector.agent.stream.worker.trace.segment;

import org.apache.skywalking.apm.collector.core.module.ModuleManager;
import org.apache.skywalking.apm.collector.storage.base.dao.IPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.ISegmentCostPersistenceDAO;
import org.apache.skywalking.apm.collector.queue.service.QueueCreatorService;
import org.apache.skywalking.apm.collector.storage.StorageModule;
import org.apache.skywalking.apm.collector.storage.table.segment.SegmentCost;
import org.apache.skywalking.apm.collector.stream.worker.base.AbstractLocalAsyncWorkerProvider;
import org.apache.skywalking.apm.collector.stream.worker.impl.PersistenceWorker;

/**
 * @author peng-yongsheng
 */
public class SegmentCostPersistenceWorker extends PersistenceWorker<SegmentCost, SegmentCost> {

    public SegmentCostPersistenceWorker(ModuleManager moduleManager) {
        super(moduleManager);
    }

    @Override public int id() {
        return 110;
    }

    @Override protected boolean needMergeDBData() {
        return false;
    }

    @Override protected IPersistenceDAO persistenceDAO() {
        return getModuleManager().find(StorageModule.NAME).getService(ISegmentCostPersistenceDAO.class);
    }

    public static class Factory extends AbstractLocalAsyncWorkerProvider<SegmentCost, SegmentCost, SegmentCostPersistenceWorker> {

        public Factory(ModuleManager moduleManager, QueueCreatorService<SegmentCost> queueCreatorService) {
            super(moduleManager, queueCreatorService);
        }

        @Override public SegmentCostPersistenceWorker workerInstance(ModuleManager moduleManager) {
            return new SegmentCostPersistenceWorker(moduleManager);
        }

        @Override
        public int queueSize() {
            return 1024;
        }
    }
}
