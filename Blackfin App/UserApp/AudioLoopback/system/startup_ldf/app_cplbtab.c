/*
** ADSP-BF537 CPLB table definitions generated on Dec 29, 2015 at 16:23:46.
*/
/*
** Copyright (C) 2000-2015 Analog Devices Inc., All Rights Reserved.
**
** This file is generated automatically based upon the options selected
** in the System Configuration utility. Changes to the CPLB configuration
** should be made by modifying the appropriate options rather than editing
** this file. To access the System Configuration utility, double-click the
** system.svc file from a navigation view.
**
** Custom additions can be inserted within the user-modifiable sections,
** these are bounded by comments that start with "$VDSG". Only changes
** placed within these sections are preserved when this file is re-generated.
**
** Product      : CrossCore Embedded Studio
** Tool Version : 6.0.4.0
*/

#include <sys/platform.h>
#include <cplbtab.h>
#include <cplb.h>

#ifdef _MISRA_RULES
#pragma diag(push)
#pragma diag(suppress:misra_rule_2_2)
#pragma diag(suppress:misra_rule_8_10)
#pragma diag(suppress:misra_rule_10_1_a)
#endif /* _MISRA_RULES */

#define CACHE_MEM_MODE (CPLB_DNOCACHE)

#pragma section("cplb_data")

cplb_entry dcplbs_table[] = {


   // L1 Data A & B, (set write-through bit to avoid 1st write exceptions)
   {0xFF800000, (PAGE_SIZE_4MB | CPLB_DNOCACHE | CPLB_LOCK | CPLB_WT | CPLB_L1SRAM)}, 

   // Async Memory Bank 2 (Second)
   // Async Memory Bank 1 (Prim B)
   // Async Memory Bank 0 (Prim A)
   {0x20200000, (PAGE_SIZE_1MB | CPLB_DNOCACHE)}, 
   {0x20100000, (PAGE_SIZE_1MB | CPLB_DNOCACHE)}, 
   {0x20000000, (PAGE_SIZE_1MB | CPLB_DNOCACHE)}, 

      // 128 MB (Maximum) SDRAM memory space 

   // Async Memory Bank 3
   {0x20300000, (PAGE_SIZE_1MB | CPLB_DNOCACHE)}, 

   // end of section - termination
   {0xffffffff, 0}, 
}; /* dcplbs_table */

#pragma section("cplb_data")

cplb_entry icplbs_table[] = {


   // L1 Code
   {0xFFA00000, (PAGE_SIZE_1MB | CPLB_I_PAGE_MGMT | CPLB_L1SRAM)}, 

   // Async Memory Bank 2 (Secnd)
   // Async Memory Bank 1 (Prim B)
   // Async Memory Bank 0 (Prim A)
   {0x20200000, (PAGE_SIZE_1MB | CPLB_INOCACHE)}, 
   {0x20100000, (PAGE_SIZE_1MB | CPLB_IDOCACHE)}, 
   {0x20000000, (PAGE_SIZE_1MB | CPLB_IDOCACHE)}, 

   // 512 MB (Maximum) SDRAM memory space 

   // Async Memory Bank 3 
   {0x20300000, (PAGE_SIZE_1MB | CPLB_INOCACHE)}, 

   // end of section - termination
   {0xffffffff, 0}, 
}; /* icplbs_table */


#ifdef _MISRA_RULES
#pragma diag(pop)
#endif /* _MISRA_RULES */

