package com.biolabi.files;

import java.io.IOException;

public interface VCFRowHandler {
    public void onRow(VCFRow row) throws IOException;
}
