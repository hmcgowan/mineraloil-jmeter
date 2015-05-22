package com.lithium.mineraloil.jmeter;

import api.RestConnection;
import com.lithium.mineraloil.api.lsw.LSWRestAPIConnection;
import com.lithium.mineraloil.api.lsw.sdx.VersionSDXAPI;

public class CaptureICBackendVersion {

    public static void main(String [] args) {
        String relativeFilePath = null;

        if (args.length > 0) {
            relativeFilePath = args[0];
        }

        LSWRestAPIConnection lswConnection = RestConnection.getLSWConnection();

        if (relativeFilePath != null && relativeFilePath.trim() != "") {
            new VersionSDXAPI(lswConnection).createVersionArtifacts(relativeFilePath);
        } else {
            new VersionSDXAPI(lswConnection).createVersionArtifacts();
        }
    }
}
