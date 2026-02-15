"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CameraWeb = void 0;
const core_1 = require("@capacitor/core");
class CameraWeb extends core_1.WebPlugin {
    async takeMultiplePhotos(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
}
exports.CameraWeb = CameraWeb;
//# sourceMappingURL=web.js.map