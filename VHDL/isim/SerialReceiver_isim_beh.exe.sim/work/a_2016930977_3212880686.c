/**********************************************************************/
/*   ____  ____                                                       */
/*  /   /\/   /                                                       */
/* /___/  \  /                                                        */
/* \   \   \/                                                       */
/*  \   \        Copyright (c) 2003-2009 Xilinx, Inc.                */
/*  /   /          All Right Reserved.                                 */
/* /---/   /\                                                         */
/* \   \  /  \                                                      */
/*  \___\/\___\                                                    */
/***********************************************************************/

/* This file is designed for use with ISim build 0x7708f090 */

#define XSI_HIDE_SYMBOL_SPEC true
#include "xsi.h"
#include <memory.h>
#ifdef __GNUC__
#include <stdlib.h>
#else
#include <malloc.h>
#define alloca _alloca
#endif
static const char *ng0 = "C:/Users/tiago/OneDrive/Documentos/Universidade/LIC/SpaceInvader/VHDL/ShiftRegister.vhd";
extern char *IEEE_P_2592010699;

unsigned char ieee_p_2592010699_sub_1744673427_503743352(char *, char *, unsigned int , unsigned int );


static void work_a_2016930977_3212880686_p_0(char *t0)
{
    char t18[16];
    char t19[16];
    char *t1;
    char *t2;
    unsigned char t3;
    unsigned char t4;
    char *t5;
    char *t6;
    char *t7;
    char *t8;
    char *t9;
    char *t10;
    unsigned char t11;
    unsigned char t12;
    unsigned char t13;
    int t14;
    unsigned int t15;
    unsigned int t16;
    unsigned int t17;
    char *t20;
    int t21;
    unsigned int t22;
    unsigned char t23;
    char *t24;
    char *t25;
    char *t26;
    char *t27;

LAB0:    xsi_set_current_line(48, ng0);
    t1 = (t0 + 1192U);
    t2 = *((char **)t1);
    t3 = *((unsigned char *)t2);
    t4 = (t3 == (unsigned char)3);
    if (t4 != 0)
        goto LAB2;

LAB4:    t1 = (t0 + 1512U);
    t2 = *((char **)t1);
    t4 = *((unsigned char *)t2);
    t11 = (t4 == (unsigned char)3);
    if (t11 == 1)
        goto LAB7;

LAB8:    t3 = (unsigned char)0;

LAB9:    if (t3 != 0)
        goto LAB5;

LAB6:
LAB3:    t1 = (t0 + 3680);
    *((int *)t1) = 1;

LAB1:    return;
LAB2:    xsi_set_current_line(49, ng0);
    t1 = xsi_get_transient_memory(5U);
    memset(t1, 0, 5U);
    t5 = t1;
    memset(t5, (unsigned char)2, 5U);
    t6 = (t0 + 3776);
    t7 = (t6 + 56U);
    t8 = *((char **)t7);
    t9 = (t8 + 56U);
    t10 = *((char **)t9);
    memcpy(t10, t1, 5U);
    xsi_driver_first_trans_fast(t6);
    goto LAB3;

LAB5:    xsi_set_current_line(51, ng0);
    t5 = (t0 + 1352U);
    t6 = *((char **)t5);
    t13 = *((unsigned char *)t6);
    t5 = (t0 + 1832U);
    t7 = *((char **)t5);
    t14 = (5 - 1);
    t15 = (4 - t14);
    t16 = (t15 * 1U);
    t17 = (0 + t16);
    t5 = (t7 + t17);
    t9 = ((IEEE_P_2592010699) + 4024);
    t10 = (t19 + 0U);
    t20 = (t10 + 0U);
    *((int *)t20) = 4;
    t20 = (t10 + 4U);
    *((int *)t20) = 1;
    t20 = (t10 + 8U);
    *((int *)t20) = -1;
    t21 = (1 - 4);
    t22 = (t21 * -1);
    t22 = (t22 + 1);
    t20 = (t10 + 12U);
    *((unsigned int *)t20) = t22;
    t8 = xsi_base_array_concat(t8, t18, t9, (char)99, t13, (char)97, t5, t19, (char)101);
    t22 = (1U + 4U);
    t23 = (5U != t22);
    if (t23 == 1)
        goto LAB10;

LAB11:    t20 = (t0 + 3776);
    t24 = (t20 + 56U);
    t25 = *((char **)t24);
    t26 = (t25 + 56U);
    t27 = *((char **)t26);
    memcpy(t27, t8, 5U);
    xsi_driver_first_trans_fast(t20);
    goto LAB3;

LAB7:    t1 = (t0 + 992U);
    t12 = ieee_p_2592010699_sub_1744673427_503743352(IEEE_P_2592010699, t1, 0U, 0U);
    t3 = t12;
    goto LAB9;

LAB10:    xsi_size_not_matching(5U, t22, 0);
    goto LAB11;

}

static void work_a_2016930977_3212880686_p_1(char *t0)
{
    char *t1;
    char *t2;
    char *t3;
    char *t4;
    char *t5;
    char *t6;
    char *t7;

LAB0:    xsi_set_current_line(54, ng0);

LAB3:    t1 = (t0 + 1832U);
    t2 = *((char **)t1);
    t1 = (t0 + 3840);
    t3 = (t1 + 56U);
    t4 = *((char **)t3);
    t5 = (t4 + 56U);
    t6 = *((char **)t5);
    memcpy(t6, t2, 5U);
    xsi_driver_first_trans_fast_port(t1);

LAB2:    t7 = (t0 + 3696);
    *((int *)t7) = 1;

LAB1:    return;
LAB4:    goto LAB2;

}


extern void work_a_2016930977_3212880686_init()
{
	static char *pe[] = {(void *)work_a_2016930977_3212880686_p_0,(void *)work_a_2016930977_3212880686_p_1};
	xsi_register_didat("work_a_2016930977_3212880686", "isim/SerialReceiver_isim_beh.exe.sim/work/a_2016930977_3212880686.didat");
	xsi_register_executes(pe);
}
