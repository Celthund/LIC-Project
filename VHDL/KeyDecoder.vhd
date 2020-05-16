----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    16:36:34 05/16/2020 
-- Design Name: 
-- Module Name:    KeyDecoder - Behavioral 
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

entity KeyDecoder is
	PORT ( 
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			KAck : in  STD_LOGIC;
			KVal: out STD_LOGIC;
			K : out  STD_LOGIC_VECTOR (3 downto 0);
			PENC_IN: in  STD_LOGIC_VECTOR (3 downto 0);
			DEC_OUT : buffer STD_LOGIC_VECTOR (2 downto 0)
	 );
end KeyDecoder;

architecture Structural of KeyDecoder is
	 COMPONENT KeyControl
		Port ( 
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			KPress : in  STD_LOGIC;
			KAck : in  STD_LOGIC;
			KVal: out  STD_LOGIC;
			KScan : out  STD_LOGIC_VECTOR (1 downto 0)
	 );
	END COMPONENT;
	COMPONENT KeyScan
		Port (
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			KScan : in  STD_LOGIC_VECTOR (1 downto 0);
			K : out  STD_LOGIC_VECTOR (3 downto 0);
			PENC_IN: in  STD_LOGIC_VECTOR (3 downto 0);
			KPress : out  STD_LOGIC;
			DEC_OUT : buffer STD_LOGIC_VECTOR (2 downto 0)
	 );
	END COMPONENT;
	Signal KScan : STD_LOGIC_VECTOR (1 downto 0);
   Signal KPress: STD_LOGIC;
begin
	U1 : KeyControl 
	PORT MAP(
			RST => RST,
			CLK => CLK,
			KPress => KPress,
			KAck => KAck,
			KVal => KVal,
			KScan => KScan
	);
	U2 : KeyScan 
	PORT MAP(
			RST => RST,
			CLK => CLK,
			KScan => KScan,
			K => K,
			PENC_IN => PENC_IN,
			KPress => KPress,
			DEC_OUT => DEC_OUT
	);
end Structural;

