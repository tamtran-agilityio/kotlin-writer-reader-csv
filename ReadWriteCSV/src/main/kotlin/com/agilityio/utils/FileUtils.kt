package com.agilityio.utils

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.PosixFilePermission

class FileUtils {
    /**
     * Implement get file
     * @param filePath name of file
     * @return file
     */
    fun get(filePath: String): File {
        setFullPermission(filePath)
        return File(filePath)
    }

    /**
     * Implement create file
     * @param filePath name of file
     * @return file after create
     */
    fun create(filePath: String): File {
        setFullPermission(filePath)
        return File(filePath)
    }

    /**
     * Implement set full permission of file
     * @param filePath name of file
     * @return file after create
     */
    fun setFullPermission(filePath: String) {
        val perms: HashSet<PosixFilePermission> = hashSetOf()
        // Add owners permission
        perms.add(PosixFilePermission.OWNER_READ)
        perms.add(PosixFilePermission.OWNER_WRITE)
        perms.add(PosixFilePermission.OWNER_EXECUTE)
        // Add group permissions
        perms.add(PosixFilePermission.GROUP_READ)
        perms.add(PosixFilePermission.GROUP_WRITE)
        perms.add(PosixFilePermission.GROUP_EXECUTE)
        // Add others permissions
        perms.add(PosixFilePermission.OTHERS_READ)
        perms.add(PosixFilePermission.OTHERS_WRITE)
        perms.add(PosixFilePermission.OTHERS_EXECUTE)
        Files.setPosixFilePermissions(Paths.get(filePath), perms)
    }

    /**
     * Implement remove permission of file
     * @param filePath name of file
     * @param permission enum permission
     * @return file update permission
     */
    fun removePermission(filePath: String, permission: PosixFilePermission) {
        val perms: HashSet<PosixFilePermission> = hashSetOf()
        // Remove permission
        perms.remove(permission)
        Files.setPosixFilePermissions(Paths.get(filePath), perms)
    }

    /**
     * Implement set permission of file
     * @param filePath name of file
     * @param permission enum permission
     * @return file update permission
     */
    fun setPermission(filePath: String, permission: PosixFilePermission) {
        val perms: HashSet<PosixFilePermission> = hashSetOf()
        // Add permission
        perms.add(permission)
        Files.setPosixFilePermissions(Paths.get(filePath), perms)
    }
}