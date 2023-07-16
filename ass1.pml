1	mtype = {READER, WRITER};
2	mtype current_writer = 0;
3		mtype reader_sem = 1;
4		mtype reader_finish_sem = 1;
5		mtype writer_sem = 1;
6	byte reader_count = 0;
7	bool writer_writing = false;
8	bool writer_waiting = false;
9	active [10] proctype Reader() {
10	    do
11	    :: true ->
12	        atomic {
13			if 
14			:: reader_sem == 0 -> 
15			skip;
16			:: else ->
17			reader_sem--;
18	                reader_count++;
19			fi;
20	        }
21	    :: true ->
22	        atomic {
23			if 
24			:: reader_finish_sem == 0 ->
25			skip;
26			:: else->
27			reader_finish_sem--;
28			reader_count--;
29			fi;
30	        }
31	    od
32	}
33	active [4] proctype Writer() {
34	    do
35	    :: true ->
36	        atomic {
37	            if
38	            :: reader_count == 0 ->
39	                writer_writing = true;
40	                current_writer = _pid;
41	                /* critical section for writing */
42	                writer_writing = false;
43	            :: else ->
44	                writer_waiting = true;
45	            fi;
46	        }
47	        /* wait for current readers to finish reading */
48	        do
49	        :: reader_count == 0 ->
50	            writer_waiting = false;
51	            writer_writing = true;
52	            current_writer = _pid;
53	            /* critical section for writing */
54	            writer_writing = false;
55	        :: else ->
56	            skip;
57	        od;
58	    od
59	}
60	ltl property1 { []<>(reader_count > 1) }; // Multiple readers can concurrently read
62	ltl property2 { []<>(writer_writing -> (reader_count == 0 && current_writer == _pid)) }; // No other readers or writers when a writer is writing
63	
64