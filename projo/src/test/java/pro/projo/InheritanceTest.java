//                                                                          //
// Copyright 2019 Mirko Raner                                               //
//                                                                          //
// Licensed under the Apache License, Version 2.0 (the "License");          //
// you may not use this file except in compliance with the License.         //
// You may obtain a copy of the License at                                  //
//                                                                          //
//     http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                          //
// Unless required by applicable law or agreed to in writing, software      //
// distributed under the License is distributed on an "AS IS" BASIS,        //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. //
// See the License for the specific language governing permissions and      //
// limitations under the License.                                           //
//                                                                          //
package pro.projo;

import java.util.Date;
import java.util.Map;
import org.junit.Test;
import pro.projo.quadruples.Factory;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertArrayEquals;
import static pro.projo.Projo.creates;

public class InheritanceTest
{
    static interface Timestamped
    {
        Date getTimestamp();
        void setTimestamp(Date time);
    }

    static interface LogEntry extends Timestamped
    {
        String getLogMessage();
        void setLogMessage(String message);
    }

    static interface ExceptionLog extends LogEntry
    {
        Exception getException();
        void setException(Exception exception);
    }

    static interface Entity
    {
        String id();
        String type();
        Map<String, Object> properties();
    }

    static interface Sensor extends Entity
    {
        Factory<Sensor, String, String, Map<String, Object>, String> FACTORY =
            creates(Sensor.class).with(Entity::id, Entity::type, Entity::properties, Sensor::device);

        String device();
    }

    @Test
    public void testMutableInheritance()
    {
        LogEntry entry = Projo.create(LogEntry.class);
        Date now = new Date();
        entry.setTimestamp(now);
        entry.setLogMessage("event");
        Object[] expected = {now, "event"};
        Object[] actual = {entry.getTimestamp(), entry.getLogMessage()};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testMutableInheritanceMultipleLevels()
    {
        ExceptionLog entry = Projo.create(ExceptionLog.class);
        Exception exception = new Exception("exception");
        Date now = new Date();
        entry.setTimestamp(now);
        entry.setLogMessage("event");
        entry.setException(exception);
        Object[] expected = {now, "event", exception};
        Object[] actual = {entry.getTimestamp(), entry.getLogMessage(), entry.getException()};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testImmutableInheritanceUsingFactory()
    {
        Sensor sensor = Sensor.FACTORY.create("id", "type", singletonMap("key", "value"), "device");
        Object[] expected = {"id", "type", singletonMap("key", "value"), "device"};
        Object[] actual = {sensor.id(), sensor.type(), sensor.properties(), sensor.device()};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testImmutableInheritanceUsingBuilder()
    {
        Sensor sensor = Projo.builder(Sensor.class)
            .with(Entity::id, "id")
            .with(Entity::type, "type")
            .with(Entity::properties, singletonMap("key", "value"))
            .with(Sensor::device, "device")
            .build();
        Object[] expected = {"id", "type", singletonMap("key", "value"), "device"};
        Object[] actual = {sensor.id(), sensor.type(), sensor.properties(), sensor.device()};
        assertArrayEquals(expected, actual);
    }
}
