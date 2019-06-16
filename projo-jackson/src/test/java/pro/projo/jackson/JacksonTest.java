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
package pro.projo.jackson;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import pro.projo.Projo;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JacksonTest
{
    static interface Time
    {
        Date time();
    }

    static interface Login extends Time
    {
        @JsonProperty("USERNAME")
        String userName();
    }

    private ObjectMapper mapper;

    @Before
    public void initializeObjectMapper()
    {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    @Test
    public void ensureThatProjoJacksonModuleIsFound() throws Exception
    {
        Stream<Module> modules = ObjectMapper.findModules().stream();
        assertTrue(modules.anyMatch(ProjoJacksonModule.class::isInstance));
    }

    @Test
    public void testSerializeRegularObject() throws Exception
    {
        ComplexPojo pojo = new ComplexPojo();
        pojo.setReal(3.14);
        pojo.setImaginary(2.71);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mapper.writeValue(output, pojo);
        String result = output.toString(StandardCharsets.UTF_8.name());
        assertEquals(json("{\"real\":3.14,\"imaginary\":2.71}"), json(result));
    }

    @Test
    public void testSerializeProjoObject() throws Exception
    {
        Complex projo = Projo.builder(Complex.class).with(Complex::real, 3.14).with(Complex::imaginary, 2.71).build();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mapper.writeValue(output, projo);
        String result = output.toString(StandardCharsets.UTF_8.name());
        assertEquals(json("{\"real\":3.14,\"imaginary\":2.71}"), json(result));
    }

    @Test
    public void testSerializeProjoObjectWithMissingProperty() throws Exception
    {
        Complex projo = Projo.builder(Complex.class).with(Complex::real, 3.14).build();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mapper.writeValue(output, projo);
        String result = output.toString(StandardCharsets.UTF_8.name());
        assertEquals(json("{\"real\":3.14,\"imaginary\":0.0}"), json(result));
    }

    @Test
    public void testSerializeInterfaceWithNonPrimitiveFields() throws Exception
    {
        Point topRight = new Point(1, 2);
        Point bottomLeft = new Point(3, 4);
        Rectangle projo = Projo.builder(Rectangle.class)
            .with(Rectangle::topRight, topRight)
            .with(Rectangle::bottomLeft, bottomLeft)
            .build();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mapper.writeValue(output, projo);
        String result = output.toString(StandardCharsets.UTF_8.name());
        assertEquals(json("{\"topRight\":{\"x\":1.0, \"y\":2.0}, \"bottomLeft\":{\"x\":3.0, \"y\":4.0}}"), json(result));
    }

    @Test
    public void testSerializeWrapped() throws Exception
    {
        WrappedComplex wrapped = new WrappedComplex();
        Complex complex = Projo.builder(Complex.class).with(Complex::real, 2D).with(Complex::imaginary, 3D).build();
        wrapped.setComplex(complex);
        wrapped.setValue(1);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mapper.writeValue(output, wrapped);
        String result = output.toString(StandardCharsets.UTF_8.name());
        assertEquals(json("{\"value\":1, \"complex\":{\"real\":2.0, \"imaginary\":3.0}}"), json(result));
    }

    @Test
    public void testSerializeWrappedWithIntegersInsteadOfDoubles() throws Exception
    {
        WrappedComplex wrapped = new WrappedComplex();
        Complex complex = Projo.builder(Complex.class).with(Complex::real, 2).with(Complex::imaginary, 3).build();
        wrapped.setComplex(complex);
        wrapped.setValue(1);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mapper.writeValue(output, wrapped);
        String result = output.toString(StandardCharsets.UTF_8.name());
        assertEquals(json("{\"value\":1, \"complex\":{\"real\":2.0, \"imaginary\":3.0}}"), json(result));
    }

    @Test
    public void testSerializeWithShortsInsteadOfIntegers() throws Exception
    {
        Interval interval = Projo.builder(Interval.class).with(Interval::begin, (short)2).with(Interval::end, (short)3).build();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mapper.writeValue(output, interval);
        String result = output.toString(StandardCharsets.UTF_8.name());
        assertEquals(json("{\"begin\":2, \"end\":3}"), json(result));
    }

    /**
    * This is not a test for Projo Jackson serialization but merely a control to make sure
    * that {@link Point}s are serialized as expected by default.
    *
    * @throws Exception if the test failed
    **/
    @Test
    public void controlSerializePoint() throws Exception
    {
        Point point = new Point(42, 73);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mapper.writeValue(output, point);
        String result = output.toString(StandardCharsets.UTF_8.name());
        assertEquals(json("{\"x\":42.0,\"y\":73.0}"), json(result));
    }

    @Test
    public void testDeserializeToInterface() throws Exception
    {
        Complex value = mapper.readValue("{\"real\":3.14, \"imaginary\":2.71}", Complex.class);
        assertArrayEquals(new double[] {3.14, 2.71}, new double[] {value.real(), value.imaginary()}, 1E-6);
    }

    @Test
    public void testDeserializeToWrappedInterface() throws Exception
    {
        WrappedComplex wrapper = mapper.readValue("{\"value\":1, \"complex\":{\"real\":2, \"imaginary\":3}}", WrappedComplex.class);
        Object[] expected = {1L, 2D, 3D};
        Object[] actual = {wrapper.getValue(), wrapper.getComplex().real(), wrapper.getComplex().imaginary()};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testDeserializeInterfaceWithNonPrimitiveFields() throws Exception
    {
        Rectangle rectangle = mapper.readValue("{\"topRight\":{\"x\":1, \"y\":2}, \"bottomLeft\":{\"x\":3, \"y\":4}}", Rectangle.class);
        Object[] expected = {1, 2, 3, 4};
        Object[] actual = {rectangle.topRight().x, rectangle.topRight().y, rectangle.bottomLeft().x, rectangle.bottomLeft().y};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testDeserializeToRegularObject() throws Exception
    {
        ComplexPojo value = mapper.readValue("{\"real\":3.14, \"imaginary\":2.71}", ComplexPojo.class);
        assertArrayEquals(new double[] {3.14, 2.71}, new double[] {value.getReal(), value.getImaginary()}, 1E-6);
    }

    @Test
    public void testDeserializeTypeHierarchyWithJsonPropertyAnnotation() throws Exception
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.setDateFormat(dateFormat);
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        Login value = mapper.readValue("{\"time\":\"2019-06-16T00:30:47.182Z\", \"USERNAME\":\"test\"}", Login.class);
        Object[] expected = {dateFormat.parse("2019-06-16T00:30:47.182Z"), "test"};
        Object[] actual = {value.time(), value.userName()};
        assertArrayEquals(expected, actual);
    }

    private JsonNode json(String string) throws Exception
    {
        ObjectMapper standardMapper = new ObjectMapper();
        return standardMapper.readTree(string);
    }
}
