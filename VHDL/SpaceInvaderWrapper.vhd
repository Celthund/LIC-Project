----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    21:33:57 06/03/2020 
-- Design Name: 
-- Module Name:    SpaceInvaderWrapper - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity SpaceInvaderWrapper is
	PORT (
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			-- Keyboard Data
			INPORT_0 : out STD_LOGIC;
			INPORT_1 : out STD_LOGIC;
			INPORT_2 : out STD_LOGIC;
			INPORT_3 : out STD_LOGIC;
			-- Keyboard DVal
			INPORT_4 : out STD_LOGIC;
			-- Keyboard ACK
			OUTPORT_7 : in STD_LOGIC;
			-- Keyboard Columns
			J2_9 : out STD_LOGIC;
			J2_10 : out STD_LOGIC;
			J2_11 : out STD_LOGIC;
			-- Keyboard Rows
			J2_12 : in STD_LOGIC;
			J2_13 : in STD_LOGIC;
			J2_14 : in STD_LOGIC;
			J2_15 : in STD_LOGIC;
			-- SLCDC SDX
			OUTPORT_1 : in  STD_LOGIC;
			-- SLCDC SCLK
		   OUTPORT_2 : in  STD_LOGIC;
			-- SLCDC SS
			OUTPORT_3 : in  STD_LOGIC;
			-- SLCDC WrL
		   J4_9 : out  STD_LOGIC;
			-- SLCDC Dout
			J4_10 : out STD_LOGIC; --RS
			J4_11 : out STD_LOGIC; --B0
			J4_12 : out STD_LOGIC;
			J4_13 : out STD_LOGIC;
			J4_14 : out STD_LOGIC; --B3
			---COIN ACCEPTOR
			J2_17: in  STD_LOGIC;--Coin in
			OUTPORT_6 : in  STD_LOGIC;--Coin out
			INPORT_6 : out STD_LOGIC;--Accept
			--M
			INPORT_7 : out STD_LOGIC;
			J2_18: in  STD_LOGIC;
			-- SSC
			OUTPORT_4 : in  STD_LOGIC;
			J3_7 : out  STD_LOGIC; --sid(0)
		   J3_9 : out  STD_LOGIC; -- sid(1)
		   J3_3 : out  STD_LOGIC; -- vol(0)
		   J3_5 : out  STD_LOGIC; -- vol(1)
		   J3_1 : out  STD_LOGIC  -- Play
		);
end SpaceInvaderWrapper;
architecture Behavioral of SpaceInvaderWrapper is
	COMPONENT KeyboardReader 
	PORT (
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			ACK : in STD_LOGIC;
			DVal : out STD_LOGIC;
			Q : out STD_LOGIC_VECTOR (3 downto 0);
			PENC_IN: in  STD_LOGIC_VECTOR (3 downto 0);
			DEC_OUT : out STD_LOGIC_VECTOR (2 downto 0)
		);
	END COMPONENT;
	COMPONENT SLCDC 
	PORT ( 
			  CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
	        SDX : in  STD_LOGIC;
           SCLK : in  STD_LOGIC;
           SS : in  STD_LOGIC;
           Dout : out  STD_LOGIC_VECTOR (4 downto 0);
           WrL : out  STD_LOGIC);
	END COMPONENT;
	COMPONENT SSC 
	PORT ( 
		  CLK : in STD_LOGIC;
		  RST : in STD_LOGIC;
		  SDX : in  STD_LOGIC;
		  SCLK : in  STD_LOGIC;
		  SS : in  STD_LOGIC;
		  sid : out std_logic_vector(1 downto 0);
		  vol : out std_logic_vector(1 downto 0);
		  Play : out STD_LOGIC);
	END COMPONENT;
	COMPONENT CoinAcceptor 
	Port ( 
	        CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
			  accept : in  STD_LOGIC;
           coinIn : in  STD_LOGIC;
           coinOut : out  STD_LOGIC);
	END COMPONENT;
begin

	KeyboardRead : KeyboardReader
	PORT MAP ( 
			RST => RST,
			CLK => CLK,
			ACK => OUTPORT_7,
			DVal => INPORT_4,
			Q(0) => INPORT_0,
			Q(1) => INPORT_1,
			Q(2) => INPORT_2,
			Q(3) => INPORT_3,
			PENC_IN(0) => J2_15,
			PENC_IN(1) => J2_14,
			PENC_IN(2) => J2_13,
			PENC_IN(3) => J2_12,		
			DEC_OUT(0) => J2_11,
			DEC_OUT(1) => J2_10,
			DEC_OUT(2) => J2_9
	 );	
	SLCD : SLCDC
	PORT MAP ( 
			RST => RST,
			CLK => CLK,
			SDX => OUTPORT_1,
			SCLK => OUTPORT_2,
			SS => OUTPORT_3,
			Dout(0) => J4_10,
			Dout(1) => J4_11,
			Dout(2) => J4_12,
			Dout(3) => J4_13,
			Dout(4) => J4_14,
			WrL => J4_9
	 );
	 SSController: SSC 
	 PORT MAP ( 
		  CLK => CLK,
		  RST => RST,
		  SDX => OUTPORT_1,
		  SCLK => OUTPORT_2,
		  SS => OUTPORT_4,
		  sid(0) => J3_7,
		  sid(1) => J3_9,
		  vol(0) => J3_3,
		  vol(1) => J3_5,
		  Play => J3_1
	 );
	 
	 
	 Coin : CoinAcceptor
	 PORT MAP( 
	        CLK => CLK,
			  RST => RST,
			  accept => OUTPORT_6,
           coinIn => J2_17,
           coinOut => INPORT_6
	 );
	 
	INPORT_7 <= J2_18;
end Behavioral;

