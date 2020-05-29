import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.TargetClass;

public class Substitutions {
}

@TargetClass(className = "java.lang.Class$Atomic")
class Target_java_lang_Class$Atomic {
        @Alias
        @RecomputeFieldValue(kind = RecomputeFieldValue.Kind.FieldOffset, declClassName = "java.lang.Class$Atomic", name = "reflectionDataOffset")
        private static long reflectionDataOffset;

        @Alias
        @RecomputeFieldValue(kind = RecomputeFieldValue.Kind.FieldOffset, declClassName = "java.lang.Class$Atomic", name = "annotationTypeOffset")
        private static long annotationTypeOffset;

        @Alias
        @RecomputeFieldValue(kind = RecomputeFieldValue.Kind.FieldOffset, declClassName = "java.lang.Class$Atomic", name = "annotationDataOffset")
        private static long annotationDataOffset;
}

