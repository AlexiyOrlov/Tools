import org.junit.Test;
import org.knowbase.tools.Methods;

import java.util.Arrays;
import java.util.Random;
import java.util.Spliterator;

public class ArrayThreadingTest {

    final int size=500006;
    final int cpuCores=4;//Runtime.getRuntime().availableProcessors();
    @Test
    public void linearOperation()
    {
        int[] array=new int[size];
        Arrays.fill(array,2);
        long ct=System.currentTimeMillis();
        for (int i : array) {
            assert i==2;
        }
        System.out.println("Linear operation took "+(System.currentTimeMillis()-ct));
    }
    @Test
    public void streamOperation()
    {
        int[] array=new int[size];
        Arrays.fill(array,2);
        long ct=System.currentTimeMillis();

        Arrays.stream(array).parallel().forEach(value -> {assert value==2;});
        System.out.println("Parallel operation took "+(System.currentTimeMillis()-ct));

    }

    @Test
    public void threadedOperation()
    {
        int[] ints=new int[new Random().nextInt(2000)+4000];
        System.out.println("Array size="+ints.length);
        Arrays.fill(ints,2);
        int chunksize;
        final int arraysize=ints.length;
        final int remainder=arraysize % cpuCores;
        if(remainder==0)
        {
            chunksize=arraysize/cpuCores;
        }
        else{
            int in=arraysize-remainder;
            chunksize=in/cpuCores;
            System.out.println("Remainder="+remainder);
        }
        System.out.println("Chunk size="+chunksize);
        System.out.println("Threads="+cpuCores);
        assert chunksize*cpuCores+remainder==arraysize;
        for (int core = 0; core < cpuCores; core++) {
            int start=core * chunksize;
            int end = start + chunksize;
            if(core==cpuCores-1)
            {
                if (remainder != 0) {
                    end=start+chunksize+remainder;
                }
            }

            System.out.println("Start="+start+" end="+end);
            int finalEnd = end;
            Thread thread=new Thread(() -> {
                for (int i = start; i < finalEnd; i++) {
                    assert ints[i]==2;

                }
            });
            thread.start();
        }
    }
}
