/**
 * - Name:    Verification Program for CS 3114 Project 3 
 * - Author:  Reese Moore <ram@vt.edu> [http://ram.vg]
 * - Version: 2011.11.01
 * 
 * - License: 
 * Copyright (c) 2011, Reese Moore <ram@vt.edu>
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * - Purpose:
 * This program takes as an argument a filename and returns if the file is, 
 * according to the specification of Project 3, in sort order.
 */

/* Library Includes */
#include <stdio.h>     /* For printf / fprintf  */
#include <stdlib.h>    /* For EXIT_ constants   */
#include <stdbool.h>   /* For bool/true/false   */
#include <unistd.h>    /* For file access       */
#include <sysexits.h>  /* For EX_ constants     */
#include <fcntl.h>     /* For O_RDONLY constant */
#include <sys/mman.h>  /* For mmap              */
#include <sys/stat.h>  /* For stat              */
#include <arpa/inet.h> /* For ntohs             */

/* Function Prototypes */
bool verify(short value);

/**
 * The main entry point for the program, and also where all of the work is done,
 * This function opens the file and memory maps it for fast access, it then
 * loops across the entire file looking at all key values ensuring that they are
 * in sort order. It then prints and returns a result.
 */
int main(int argc, char **argv)
{
    int fd, sz, i;
    short *map, shrt;
    struct stat status;

    /* Print usage if we didn't get the arguments correct */
    if (argc != 2) {
        printf("Verification program for CS3114 Project 3\n");
        printf("Usage: %s <check-file>\n", argv[0]);
        exit(EX_USAGE);
    }

    /* Open the file for reading */
    fd = open(argv[1], O_RDONLY);
    if (fd == NULL) {
        fprintf(stderr, "Error opening file: %s\n", argv[1]);
        exit(EX_NOINPUT);
    }

    /* Get the file size */
    stat(argv[1], &status);
    sz = status.st_size;

    /* Memory map that file for faster access */
    map = (short *) mmap(0, sz * sizeof(char), PROT_READ, MAP_SHARED, fd, 0);
    if (map == MAP_FAILED) {
        close(fd);
        fprintf(stderr, "Error mapping file to memory.\n");
        exit(EX_OSERR);
    }

    /* Iterate across the entire file, verifying that every key is in sort
     * order. */
    for (i = 0; i < sz/2; i+=2) {
        /* Convert from big endian (in the file) to local representation */
        shrt = ntohs(map[i]);

#       ifdef DEBUG
            printf("Testing: %d - %hd - 0x%hx\n", i, shrt, shrt);
#       endif
            
        if (!verify(shrt)) {
            printf("FILE NOT IN SORT ORDER. (%d) \n", i);
            return EXIT_FAILURE;
        }
    }

    /* Clean up some here */
    munmap(map, sz);
    close(fd);

    /* If we made it here, everything worked! */
    printf("File %s is in sort order.\n", argv[1]);
    return EXIT_SUCCESS;
}

/**
 * Verify that 'value' is greater than or equal to the last time that verify was
 * called. Verify MUST succeed on the first call, then afterwards it will keep a
 * static variable of the previous call for comparison.
 *
 * Note that a failed verify DOES NOT update the last_call value.
 *
 * Arguments:
 * value - The value to check.
 */
bool verify(short value) 
{
    static short last_call = 0; /* Smallest valid key is 1. */

    /* Make sure that the value is in the range 1-30000 */
    if ((value < 1) || (value > 30000)) {
        return false;
    }

    /* Verify that the values are increasing in order */
    if (value >= last_call) {
        last_call = value;
        return true;
    } else {
        return false;
    }
}
