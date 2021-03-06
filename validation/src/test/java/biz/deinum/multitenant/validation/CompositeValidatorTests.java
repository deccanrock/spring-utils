package biz.deinum.multitenant.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

/**
 * Tests for {@link CompositeValidator}.
 * 
 * @author Marten Deinum
 *
 */
public class CompositeValidatorTests {

    @Test
    public void singleSupportedValidator() {
        final Validator validator = mock(Validator.class);
        when(validator.supports(any(Class.class))).thenReturn(true);
        final CompositeValidator cv = new CompositeValidator(new Validator[] { validator });
        assertTrue(cv.supports(Date.class));
        cv.validate(new Date(), new MapBindingResult(new HashMap(), "command"));

        verify(validator, times(1)).validate(any(Object.class), any(Errors.class));

    }

    @Test
    public void singleNotSupportedValidator() {
        final Validator validator = mock(Validator.class);
        when(validator.supports(any(Class.class))).thenReturn(false);
        final CompositeValidator cv = new CompositeValidator(new Validator[] { validator });
        assertFalse(cv.supports(Date.class));
        cv.validate(new Date(), new MapBindingResult(new HashMap(), "command"));

        verify(validator, times(0)).validate(any(Object.class), any(Errors.class));

    }

    @Test
    public void multipleAndSingleSupportedValidator() {
        final Validator validator1 = mock(Validator.class, "v1");
        final Validator validator2 = mock(Validator.class, "v2");

        when(validator1.supports(any(Class.class))).thenReturn(false);
        when(validator2.supports(any(Class.class))).thenReturn(true);

        final CompositeValidator cv = new CompositeValidator(new Validator[] { validator1, validator2 });
        assertTrue(cv.supports(Date.class));
        cv.validate(new Date(), new MapBindingResult(new HashMap(), "command"));

        verify(validator1, times(0)).validate(any(Object.class), any(Errors.class));
        verify(validator2, times(1)).validate(any(Object.class), any(Errors.class));

    }

    @Test
    public void multipleAndMultipleSupportedValidator() {
        final Validator validator1 = mock(Validator.class, "v1");
        final Validator validator2 = mock(Validator.class, "v2");

        when(validator1.supports(any(Class.class))).thenReturn(true);
        when(validator2.supports(any(Class.class))).thenReturn(true);

        final CompositeValidator cv = new CompositeValidator(new Validator[] { validator1, validator2 });
        assertTrue(cv.supports(Date.class));
        cv.validate(new Date(), new MapBindingResult(new HashMap(), "command"));

        verify(validator1, times(1)).validate(any(Object.class), any(Errors.class));
        verify(validator2, times(1)).validate(any(Object.class), any(Errors.class));

    }

    @Test
    public void multipleNotSupportedValidator() {
        final Validator validator1 = mock(Validator.class, "v1");
        final Validator validator2 = mock(Validator.class, "v2");

        when(validator1.supports(any(Class.class))).thenReturn(false);
        when(validator2.supports(any(Class.class))).thenReturn(false);

        final CompositeValidator cv = new CompositeValidator(new Validator[] { validator1, validator2 });
        assertFalse(cv.supports(Date.class));
        cv.validate(new Date(), new MapBindingResult(new HashMap(), "command"));

        verify(validator1, times(0)).validate(any(Object.class), any(Errors.class));
        verify(validator2, times(0)).validate(any(Object.class), any(Errors.class));

    }

}
