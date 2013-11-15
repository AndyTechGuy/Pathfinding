/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.behavior.tree;

/**
 * @author synopia
 */
public class RepeatNode extends DecoratorNode {
    public RepeatNode(Node child) {
        super(child);
    }

    @Override
    public RepeatTask create() {
        return new RepeatTask(this);
    }

    public static class RepeatTask extends DecoratorTask implements Task.Observer {
        public RepeatTask(RepeatNode node) {
            super(node);
        }

        @Override
        public void onInitialize() {
            interpreter().start(getNode().child, this);
        }

        @Override
        public Status update(float dt) {
            return Status.RUNNING;
        }

        @Override
        public void handle(Status result) {
            if (result == Status.FAILURE) {
                interpreter().stop(this, Status.FAILURE);
                return;
            }

            interpreter().start(getNode().child, this);
        }

        @Override
        public RepeatNode getNode() {
            return (RepeatNode) super.getNode();
        }
    }

}
