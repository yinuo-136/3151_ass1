mtype = {READER, WRITER};mtype current_writer = 0;	mtype reader_sem = 1;	mtype reader_finish_sem = 1;	mtype writer_sem = 1;byte reader_count = 0;bool writer_writing = false;bool writer_waiting = false;active [10] proctype Reader() {
    do
    :: true ->
        atomic {
		if 
		:: reader_sem == 0 -> 
		skip;
		:: else ->
		reader_sem--;
                reader_count++;
		fi;
        }
    :: true ->
        atomic {
		if 
		:: reader_finish_sem == 0 ->
		skip;
		:: else->
		reader_finish_sem--;
		reader_count--;
		fi;
        }
    od
}
active [4] proctype Writer() {
    do
    :: true ->
        atomic {
            if
            :: reader_count == 0 ->
                writer_writing = true;
                current_writer = _pid;
                /* critical section for writing */
                writer_writing = false;
            :: else ->
                writer_waiting = true;
            fi;
        }
        /* wait for current readers to finish reading */
        do
        :: reader_count == 0 ->
            writer_waiting = false;
            writer_writing = true;
            current_writer = _pid;
            /* critical section for writing */
            writer_writing = false;
        :: else ->
            skip;
        od;
    od
}
ltl property1 { []<>(reader_count > 1) }; // Multiple readers can concurrently read
ltl property2 { []<>(writer_writing -> (reader_count == 0 && current_writer == _pid)) }; // No other readers or writers when a writer is writing